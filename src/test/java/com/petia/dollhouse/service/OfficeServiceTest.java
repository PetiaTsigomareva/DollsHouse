package com.petia.dollhouse.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.repositories.CompanyRepository;
import com.petia.dollhouse.repositories.OfficeRepository;
import com.petia.dollhouse.validation.ValidationUtil;
import com.petia.dollhouse.validation.ValidationUtilImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OfficeServiceTest {
	public static final String ERROR_MESSAGE = "Can not add office with same name!";
	public static final String ERROR_EDIT_MESSAGE = "Can not edit office with empty fields!";
	public static final String INVALID_PHONE_NUMBER = "Phone number must be between 7 and 12 lenght!";
	public static final String DELETE_OFFICE_MESSAGE = "Office no exist! Can not be deleted!";

	private OfficeService officeService;

	private CompanyService companyService;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private ModelMapper modelMapper;

	private ValidationUtil validationUtil;

	@Before
	public void init() {
		this.modelMapper = new ModelMapper();
		this.validationUtil = new ValidationUtilImpl();
		this.companyService = new CompanyServiceImpl(this.companyRepository, this.modelMapper, this.validationUtil);
		this.officeService = new OfficeServiceImpl(companyService, officeRepository, this.modelMapper, this.validationUtil);

	}

	@Test
	public void test_addOffice_with_correct_data_then_return_officeId() {
		String companyId = this.companyService.addCompany(createCompanyModel());

		String actualId = this.officeService.addOffice(getOfficeModel(companyId));
		OfficeServiceModel expected = modelMapper.map(officeRepository.findById(actualId).orElse(null), OfficeServiceModel.class);

		assertEquals(expected.getId(), actualId);

	}

	@Test(expected = Exception.class)
	public void test_addOffice_with_incorrect_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModel());

		String actualId = this.officeService.addOffice(getOfficeModelWhitNull(companyId));

		assertEquals(null, actualId);

	}

	@Test(expected = Exception.class)
	public void test_addOffice_with_null_company_then_return_exeption() {

		String actualId = this.officeService.addOffice(getOfficeModelWhitNull(null));

		assertEquals(null, actualId);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_addOffice_with_same_name_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModel());

		String actualId = this.officeService.addOffice(getOfficeModelWhitNull(companyId));
		String expectedId = this.officeService.addOffice(getOfficeModelWhitNull(companyId));

		assertTrue(ERROR_MESSAGE, expectedId == null);

	}

	@Test
	public void test_editOffice_with_correct_data_then_return_officeId() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel toBeEdited = this.officeService.findOfficeByID(actualId);
		toBeEdited.setName("Ani");

		// given or arrange
		OfficeServiceModel actual = this.officeService.editOffice(toBeEdited);
		OfficeServiceModel expected = this.officeService.findOfficeByID(actual.getId());

		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getCompanyId(), actual.getCompanyId());
		assertEquals(expected.getStatus(), actual.getStatus());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_editOffice_with_incorrect_data_then_return_exeption() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel toBeEdited = this.officeService.findOfficeByID(actualId);
		toBeEdited.setName("a");

		// given or arrange
		OfficeServiceModel actual = this.officeService.editOffice(toBeEdited);
		OfficeServiceModel expected = this.officeService.findOfficeByID(actual.getId());

		// then or assert
		assertTrue(ERROR_MESSAGE, expected == null);

	}

	@Test()
	public void test_phoneRegex() {
		String regex = Constants.PHONE_NUMBER_REGEX;
		String test = "123";

		boolean result = Pattern.matches(regex, test);
		assertFalse("Expecting regex " + regex + " to not match " + test, result);
		test = INVALID_PHONE_NUMBER;

		result = Pattern.matches(regex, test);
		assertFalse("Expecting regex " + regex + " to not match " + test, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_editOffice_with_incorrect_phoneNumber_then_return_exeption() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel toBeEdited = this.officeService.findOfficeByID(actualId);
		toBeEdited.setPhoneNumber(INVALID_PHONE_NUMBER);

		// given or arrange
		OfficeServiceModel actual = this.officeService.editOffice(toBeEdited);
		OfficeServiceModel expected = this.officeService.findOfficeByID(actual.getId());

		// then or assert
		assertTrue(INVALID_PHONE_NUMBER, expected == null);
	}

	@Test
	public void test_findByID_with_correct_data_then_return_office() {
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel expected = this.officeService.findOfficeByID(actualId);

		assertEquals(actualId, expected.getId());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_findByID_with_incorrect_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel expected = this.officeService.findOfficeByID(Constants.ID_ERROR_MESSAGE);

		assertTrue(Constants.ID_ERROR_MESSAGE, expected == null);

	}

	@Test
	public void test_findAllOffices_with_correct_data_then_returnOk() {
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		long expected = this.officeService.findAllOffices().size();

		assertEquals(1, expected);
	}

	@Test
	public void test_deleteOffice_whendataIsCorrect_then_return_deletedOffice() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());
		String actualId = this.officeService.addOffice(getOfficeModel(companyId));

		OfficeServiceModel actual = this.officeService.findOfficeByID(actualId);

		// when or act
		OfficeServiceModel expected = this.officeService.deleteOffice(actual);

		// then or assert

		assertNotEquals(actual.getStatus(), expected.getStatus());
	}

	@Test(expected = Exception.class)
	public void test_deleteOffice_whendataIsInCorrect_then_return_exeption() {
		// given or arrange
		OfficeServiceModel officeServiceModel = new OfficeServiceModel();
		// when or act
		OfficeServiceModel expected = this.officeService.deleteOffice(officeServiceModel);

		// then or assert
		assertTrue(DELETE_OFFICE_MESSAGE, expected == null);
	}

	private OfficeServiceModel getOfficeModel(String companyId) {
		OfficeServiceModel officeServiceModel = new OfficeServiceModel();

		officeServiceModel.setName("validName");
		officeServiceModel.setAddress("very long validAddress");
		officeServiceModel.setPhoneNumber("1234567890");
		officeServiceModel.setEmail("valid@mail.com");
		officeServiceModel.setCompanyId(companyId);

		return officeServiceModel;

	}

	private OfficeServiceModel getOfficeModelWhitNull(String companyId) {
		OfficeServiceModel officeServiceModel = new OfficeServiceModel();

		officeServiceModel.setName("validName");
		officeServiceModel.setAddress(null);
		officeServiceModel.setPhoneNumber("1234567890");
		officeServiceModel.setEmail("valid@mail.com");
		officeServiceModel.setCompanyId(companyId);

		return officeServiceModel;

	}

	private CompanyServiceModel createCompanyModel() {
		CompanyServiceModel c = new CompanyServiceModel();

		c.setName("name");
		c.setAddress("very long address");
		c.setIdentificationCode("123456789");
		c.setDateOfCreation(LocalDate.now());
		c.setOwner("owner");

		return c;
	}

}
