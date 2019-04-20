package com.petia.dollhouse.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.service.RoleServiceModel;
import com.petia.dollhouse.repositories.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleServiceTest {
	public static final String VALID_ROLE_NAME = "Valid name";
	public static final String INVALID_ROLE_NAME = "Invalid name";
	public static final String ROLE_ERROR_MASSAGE = "Can not delete role, not exist!";
	@Autowired
	private RoleRepository roleRepository;

	private RoleService roleService;

	private ModelMapper modelMapper;

	@Before
	public void init() {
		this.modelMapper = new ModelMapper();
		this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);

	}

	@Test
	public void test_deleteRole_whendataIsCorrect_then_return_deletedRole() {
		// given or arrange
		this.roleService.seedRoles();

		RoleServiceModel actual = this.roleService.findByAuthority(this.getRoleAuthority());
		// when or act
		this.roleService.deleteRole(actual);

		RoleServiceModel expected = this.roleService.findByAuthority(this.getRoleAuthority());

		// then or assert

		assertNotEquals(actual.getStatus(), expected.getStatus());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_deleteRole_whendataIsInCorrect_then_return_exeption() {
		// given or arrange
		this.roleService.seedRoles();

		RoleServiceModel actual = this.roleService.findByAuthority(this.getInvalidRoleAuthority());
		// when or act
		this.roleService.deleteRole(actual);

		RoleServiceModel expected = this.roleService.findByAuthority(this.getInvalidRoleAuthority());

		// then or assert
		assertTrue(ROLE_ERROR_MASSAGE, expected == null);
	}

	String getRoleAuthority() {
		String result;
		RoleServiceModel model = new RoleServiceModel();

		model.setAuthority(RoleNames.ROLE_USER.name());
		result = model.getAuthority();

		return result;
	}

	String getInvalidRoleAuthority() {
		String result;
		RoleServiceModel model = new RoleServiceModel();

		model.setAuthority(VALID_ROLE_NAME);
		result = model.getAuthority();

		return result;
	}

}
