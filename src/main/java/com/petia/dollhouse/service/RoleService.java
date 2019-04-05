package com.petia.dollhouse.service;

import java.util.Set;

import com.petia.dollhouse.domain.service.RoleServiceModel;

public interface RoleService {

	void seedRoles();

	Set<RoleServiceModel> findAllRoles();

	RoleServiceModel findByAuthority(String authority);

	void deleteRole(RoleServiceModel roleServiceModel);
}
