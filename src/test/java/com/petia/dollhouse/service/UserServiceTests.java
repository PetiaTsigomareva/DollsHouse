package com.petia.dollhouse.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.RoleServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.repositories.CompanyRepository;
import com.petia.dollhouse.repositories.OfficeRepository;
import com.petia.dollhouse.repositories.ReservationRepository;
import com.petia.dollhouse.repositories.RoleRepository;
import com.petia.dollhouse.repositories.ServiceRepository;
import com.petia.dollhouse.repositories.UserRepository;
import com.petia.dollhouse.validation.ValidationUtil;
import com.petia.dollhouse.validation.ValidationUtilImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class UserServiceTests {
	private static final String INVALIT_USERNAME = "Invalid Username";
	private static final String INVALIT_CRITERIA = "Invalid Criteria";

	private UserService userService;
	private ModelMapper modelMapper;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private RoleService roleService;
	private OfficeService officeService;
	private CompanyService companyService;
	private DollHouseService serviceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	private ValidationUtil validationUtil;

	@Before
	public void init() {
		this.modelMapper = new ModelMapper();
		this.validationUtil = new ValidationUtilImpl();
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);
		this.userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.bCryptPasswordEncoder, this.validationUtil);
		this.companyService = new CompanyServiceImpl(this.companyRepository, this.modelMapper, this.validationUtil);
		this.officeService = new OfficeServiceImpl(companyService, officeRepository, this.modelMapper, this.validationUtil);
		this.serviceService = new DollHouseServiceImpl(serviceRepository, reservationRepository, officeService, this.modelMapper, this.validationUtil);
	}

	@Test
	public void test_registerUser_with_correct_data_then_returnRegisteredUser() {
		UserServiceModel toBeSaveUser = getUserServiceModel();

		UserServiceModel actual = this.userService.registerUser(toBeSaveUser);
		UserServiceModel expected = modelMapper.map(userRepository.findById(actual.getId()).orElse(null), UserServiceModel.class);

		assertEquals(expected.getId(), actual.getId());

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_registerUser_with_null_data_then_returnExeption() {
		UserServiceModel toBeSaveUser = getUserServiceModelWithNullField();
		this.userService.registerUser(toBeSaveUser);
	}

	@Test(expected = Exception.class)
	public void test_registerUser_with_same_username_then_returnExeption() {
		UserServiceModel toBeSaveUser = getUserServiceModel();

		UserServiceModel actual = this.userService.registerUser(toBeSaveUser);
		UserServiceModel expected = modelMapper.map(userRepository.findById(actual.getId()).orElse(null), UserServiceModel.class);
		actual = this.userService.registerUser(expected);
		assertEquals(null, actual.getId());

	}

	@Test(expected = Exception.class)
	public void test_registerUser_with_same_email_then_returnExeption() {
		UserServiceModel toBeSaveUser = getUserServiceModel();

		UserServiceModel actual = this.userService.registerUser(toBeSaveUser);
		UserServiceModel expected = modelMapper.map(userRepository.findById(actual.getId()).orElse(null), UserServiceModel.class);
		actual = this.userService.registerUser(expected);
		assertEquals(null, actual.getId());

	}

	@Test
	public void test_findByUsername_with_correct_data_than_ReturnUser() {
		UserServiceModel testUser = getUserServiceModel();
		this.userService.registerUser(testUser);
		UserServiceModel expected = this.userService.findUserByUserName(testUser.getUsername());

		assertEquals(testUser.getUsername(), expected.getUsername());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void test_findByUsername_with_incorrect_data_then_ReturnExeption() {
		UserServiceModel testUser = getUserServiceModel();
		this.userService.registerUser(testUser);
		this.userService.findUserByUserName(INVALIT_USERNAME);

	}

	@Test
	public void test_findByID_with_correct_data_then_ReturnUser() {
		UserServiceModel testUser = getUserServiceModel();

		UserServiceModel actual = this.userService.registerUser(testUser);
		UserServiceModel expected = this.userService.findUserById(actual.getId());

		assertEquals(actual.getId(), expected.getId());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_findByID_with_incorrect_data_then_ReturnExeption() {
		UserServiceModel testUser = getUserServiceModel();

		this.userService.registerUser(testUser);
		UserServiceModel expected = this.userService.findUserById(Constants.ID_ERROR_MESSAGE);

		assertTrue(Constants.ID_ERROR_MESSAGE, expected == null);
	}

	@Test
	public void test_findAllCustomers_with_correct_criteria_then_ReturnOk() {
		UserServiceModel testUser = getUserServiceModel();
		this.userService.registerUser(testUser);
		long expected = this.userService.findUsersByCriteria(Constants.CUSTOMER).size();

		assertEquals(1, expected);
	}

	@Test
	public void test_findAllEmployee_with_correct_criteria_then_ReturnOk() {
		String companyId = this.companyService.addCompany(createCompany());
		String officeId = this.officeService.addOffice(createOffice(companyId));
		String serviceId = this.serviceService.add(createService(officeId));

		this.roleService.seedRoles();

		this.userService.addEmployee(getEmployeeServiceModel(officeId, serviceId));

		long expected = this.userService.findUsersByCriteria(Constants.EMPLOYEE).size();

		assertEquals(1, expected);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_findAllUsers_with_incorrect_criteria_then_ReturnExeption() {
		UserServiceModel testUser = getUserServiceModel();
		this.userService.registerUser(testUser);
		long expected = this.userService.findUsersByCriteria(INVALIT_CRITERIA).size();

		assertEquals(0, expected);
	}

	@Test()
	@Ignore()
	public void test_setUserRole_with_correct_roles_then_ReturnOK() {
		UserServiceModel actual = this.userService.registerUser(getUserServiceModel());
		this.userService.setUserRole(actual.getId(), Constants.USER);

//		actual = this.userService.findUserById(actual.getId());
		UserServiceModel expected = this.userService.findUserById(actual.getId());

		assertEquals(expected.getAuthorities().size(), actual.getAuthorities().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_setUserRole_with_incorrect_roles_then_ReturnExeption() {
		UserServiceModel actual = this.userService.registerUser(getUserServiceModel());
		this.userService.setUserRole(actual.getId(), Constants.INVALID_ROLE);

	}

	@Test
	public void test_addEmployee_with_correct_data_then_returnAddedEmployeeId() {
		String companyId = this.companyService.addCompany(createCompany());
		String officeId = this.officeService.addOffice(createOffice(companyId));
		String serviceId = this.serviceService.add(createService(officeId));
		this.roleService.seedRoles();

		String actualId = this.userService.addEmployee(getEmployeeServiceModel(officeId, serviceId));

		UserServiceModel expected = modelMapper.map(userRepository.findById(actualId).orElse(null), UserServiceModel.class);

		assertEquals(expected.getId(), actualId);
	}


	@Test
	public void test_editEmployee_with_correct_data_then_returnEditedEmployee() {
		String companyId = this.companyService.addCompany(createCompany());
		String officeId = this.officeService.addOffice(createOffice(companyId));
		String serviceId = this.serviceService.add(createService(officeId));
		this.roleService.seedRoles();
		UserServiceModel actual=getEmployeeServiceModel(officeId, serviceId);

		if (!this.validationUtil.isValid(actual)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}
		String actualId = this.userService.addEmployee(actual);
        actual.setId(actualId);
        actual.setFirstName("Petia");

		actual = this.userService.editEmployee(actual);

		UserServiceModel expected = modelMapper.map(userRepository.findById(actual.getId()).orElse(null), UserServiceModel.class);

		assertEquals(expected.getId(), actual.getId());
	}



	private UserServiceModel getUserServiceModel() {
		UserServiceModel testUser = new UserServiceModel();
		testUser.setUsername("Petura");
		testUser.setPassword("123456");
		testUser.setEmail("valid@mail.com");
		testUser.setFirstName("Ani");
		testUser.setLastName("Petrova");
		testUser.setPhoneNumber("12345678");

		return testUser;
	}

	private UserServiceModel getUserServiceModelWithNullField() {
		UserServiceModel testUser = new UserServiceModel();
		testUser.setUsername("Petura");
		testUser.setPassword("123456");
		testUser.setEmail("valid@mail.com");
		testUser.setFirstName(null);
		testUser.setLastName("Petrova");
		testUser.setPhoneNumber("12345678");

		return testUser;
	}

	private User getUser() {
		User testUser = new User();
		testUser.setUsername("Petura");
		testUser.setPassword("123456");
		testUser.setEmail("valid@mail.com");
		testUser.setFirstName("Ani");
		testUser.setLastName("Petrova");
		testUser.setPhoneNumber("12345678");

		return testUser;
	}

	private UserServiceModel getEmployeeServiceModel(String officeId, String serviceId) {

		UserServiceModel testUser = new UserServiceModel();

		testUser.setUsername("Petura");
		testUser.setPassword("123456");
		testUser.setEmail("valid@mail.com");
		testUser.setFirstName("Ani");
		testUser.setLastName("Petrova");
		testUser.setPhoneNumber("12345678");

		OfficeServiceModel officeServiceModel = new OfficeServiceModel();
		officeServiceModel.setId(officeId);
		testUser.setOfficeServiceModel(officeServiceModel);

		ServiceModel serviceModel = new ServiceModel();
		serviceModel.setId(serviceId);
		testUser.setServiceModel(serviceModel);

		return testUser;
	}

	private OfficeServiceModel createOffice(String companyId) {
		OfficeServiceModel o = new OfficeServiceModel();

		o.setAddress("very long address");
		o.setCompanyId(companyId);
		o.setEmail("email@abv.bg");
		o.setId("id");
		o.setName("name");
		o.setPhoneNumber("1234567890");
		o.setStatus(StatusValues.ACTIVE.toString());

		return o;
	}

	private CompanyServiceModel createCompany() {
		CompanyServiceModel c = new CompanyServiceModel();

		c.setId("companyId");
		c.setAddress("very long address");
		c.setIdentificationCode("123456789");
		c.setName("name");
		c.setDateOfCreation(LocalDate.now());
		c.setOwner("owner");

		return c;
	}

	private ServiceModel createService(String officeId) {
		ServiceModel s = new ServiceModel();

		s.setId("serviced");
		s.setName("name");
		s.setPrice(new BigDecimal(12));
		s.setStatus(StatusValues.ACTIVE.toString());
		s.setOfficeId(officeId);
		s.setDescription("description");
		s.setUrlPicture("url_pictures");

		return s;
	}

	private RoleServiceModel createRole() {
		RoleServiceModel r = new RoleServiceModel();

		r.setAuthority(RoleNames.ROLE_USER.toString());

		return r;
	}
}
