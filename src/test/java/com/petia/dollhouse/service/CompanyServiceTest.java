package com.petia.dollhouse.service;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.NoSuchElementException;

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
import com.petia.dollhouse.repositories.CompanyRepository;
import com.petia.dollhouse.validation.ValidationUtil;
import com.petia.dollhouse.validation.ValidationUtilImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CompanyServiceTest {
	public static final String COMPANY_SAME_NAME_ERROR = "Can not persist company with same name!";
	public static final String COMPANY_NULL_ERROR = "Can not persist Company whit null field!";
	public static final String COMPANY_EMPTY_ERROR = "Can not persist Company whit empty field!";
	public static final String COMPANY_INDENTIFICATION_NUMBER_ERROR = "Can not persist Company whit invalid indetification number field!";
	public static final String COMPANY_INVALID_INDENTIFICATION_NUMBER = "12358";
	public static final String COMPANY_INVALID_NAME = "Invalid name";
	public static final String COMPANY_DELETE_MESSAGE = "Copmpany no exist! Can not be deleted!";
	@Autowired
	private CompanyRepository companyRepository;

	private CompanyService companyService;

	private ModelMapper modelMapper;

	private ValidationUtil validationUtil;

	@Before
	public void init() {
		this.modelMapper = new ModelMapper();
		this.validationUtil = new ValidationUtilImpl();
		this.companyService = new CompanyServiceImpl(this.companyRepository, this.modelMapper, this.validationUtil);

	}

	@Test
	public void test_addCompany_with_correct_data_then_return_companyId() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel expected = modelMapper.map(companyRepository.findById(actualId).orElse(null), CompanyServiceModel.class);

		assertEquals(expected.getId(), actualId);

	}

	@Test(expected = AssertionError.class)
	public void test_addCompany_with_incorrect_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel expected = modelMapper.map(companyRepository.findById(companyId).orElse(null), CompanyServiceModel.class);

		assertTrue(COMPANY_NULL_ERROR, expected == null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_addCompany_with_emptyField_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModelWhithEmptyField());

		CompanyServiceModel expected = modelMapper.map(companyRepository.findById(companyId).orElse(null), CompanyServiceModel.class);

		assertTrue(COMPANY_EMPTY_ERROR, expected == null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_addCompany_with_incorect_indetification_number_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModelWhithIncorectIndetificationNumber());

		CompanyServiceModel expected = modelMapper.map(companyRepository.findById(companyId).orElse(null), CompanyServiceModel.class);

		assertTrue(COMPANY_INDENTIFICATION_NUMBER_ERROR, expected == null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_addCompany_with_same_name_data_then_return_exeption() {
		String companyId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel company = modelMapper.map(companyRepository.findById(companyId).orElse(null), CompanyServiceModel.class);

		String expectedId = this.companyService.addCompany(company);

		assertTrue(COMPANY_SAME_NAME_ERROR, expectedId == null);

	}

	@Test
	public void test_editcompany_with_correct_data_then_return_officeId() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel toBeEdited = this.companyService.findCompanyByID(companyId);
		toBeEdited.setName(Constants.COMPANY_NAME);

		// given or arrange
		CompanyServiceModel actual = this.companyService.editCompany(toBeEdited);
		CompanyServiceModel expected = this.companyService.findCompanyByID(actual.getId());

		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getIdentificationCode(), actual.getIdentificationCode());
		assertEquals(expected.getDateOfCreation(), actual.getDateOfCreation());
		assertEquals(expected.getOwner(), actual.getOwner());
		assertEquals(expected.getStatus(), actual.getStatus());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_editCompany_with_empty_data_then_return_exeption() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel toBeEdited = this.companyService.findCompanyByID(companyId);
		toBeEdited.setName("");

		// given or arrange
		CompanyServiceModel actual = this.companyService.editCompany(toBeEdited);
		CompanyServiceModel expected = this.companyService.findCompanyByID(actual.getId());

		// then or assert
		assertTrue(COMPANY_EMPTY_ERROR, expected == null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_editCompany_with_incorrect_indetification_number_then_return_exeption() {
		// given or arrange
		String companyId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel toBeEdited = this.companyService.findCompanyByID(companyId);
		toBeEdited.setIdentificationCode(COMPANY_INVALID_INDENTIFICATION_NUMBER);

		// given or arrange
		CompanyServiceModel actual = this.companyService.editCompany(toBeEdited);
		CompanyServiceModel expected = this.companyService.findCompanyByID(actual.getId());

		// then or assert
		assertTrue(COMPANY_INDENTIFICATION_NUMBER_ERROR, expected == null);

	}

	@Test
	public void test_findByID_with_correct_data_then_return_company() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel expected = this.companyService.findCompanyByID(actualId);

		assertEquals(actualId, expected.getId());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_findByID_with_incorrect_data_then_return_exeption() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel expected = this.companyService.findCompanyByID(Constants.ID_ERROR_MESSAGE);

		assertTrue(Constants.ID_ERROR_MESSAGE, expected == null);

	}

	@Test
	public void test_findAllCompanies_with_correct_data_then_return_Ok() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		long expected = this.companyService.findAllCompanies().size();

		assertEquals(1, expected);
	}

	@Test
	public void test_findByName_with_correct_data_than_return_company() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel actual = this.companyService.findCompanyByID(actualId);
		CompanyServiceModel expected = this.companyService.findCompanyByName(actual.getName());

		assertEquals(actual.getName(), expected.getName());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_findByName_with_incorrect_data_then_return_exeption() {
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel actual = this.companyService.findCompanyByID(actualId);
		CompanyServiceModel expected = this.companyService.findCompanyByName(COMPANY_INVALID_NAME);

		assertTrue(Constants.ERROR_MESSAGE, expected == null);

	}

	@Test
	public void test_deleteCompany_whendataIsCorrect_then_return_deletedCompany() {
		// given or arrange
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel actual = this.companyService.findCompanyByID(actualId);
		// when or act
		CompanyServiceModel expected = this.companyService.deleteCompany(actual);

		// then or assert

		assertNotEquals(actual.getStatus(), expected.getStatus());
	}

	@Test(expected = AssertionError.class)
	public void test_deleteCompany_whendataIsInCorrect_then_return_exeption() {
		// given or arrange
		String actualId = this.companyService.addCompany(createCompanyModel());

		CompanyServiceModel actual = this.companyService.findCompanyByID(actualId);
		// when or act
		CompanyServiceModel expected = this.companyService.deleteCompany(actual);

		// then or assert

		assertTrue(COMPANY_DELETE_MESSAGE, expected == null);
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

	private CompanyServiceModel createCompanyModelWhithEmptyField() {
		CompanyServiceModel c = new CompanyServiceModel();

		c.setName("name");
		c.setAddress(" ");
		c.setIdentificationCode("123456789");
		c.setDateOfCreation(LocalDate.now());
		c.setOwner("owner");

		return c;
	}

	private CompanyServiceModel createCompanyModelWhithIncorectIndetificationNumber() {
		CompanyServiceModel c = new CompanyServiceModel();

		c.setName("name");
		c.setAddress(" ");
		c.setIdentificationCode("12345");
		c.setDateOfCreation(LocalDate.now());
		c.setOwner("owner");

		return c;
	}

}
