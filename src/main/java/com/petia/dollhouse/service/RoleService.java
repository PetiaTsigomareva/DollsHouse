package com.petia.dollhouse.service;



import com.petia.dollhouse.domain.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRoles();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
