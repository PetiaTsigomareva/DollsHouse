package com.petia.dollhouse.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.Role;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.RoleServiceModel;
import com.petia.dollhouse.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void seedRoles() {
		if (this.roleRepository.count() == 0) {
			for (RoleNames r : RoleNames.values()) {
				this.roleRepository.save(new Role(r.name()));

			}
		}
	}

	@Override
	public Set<RoleServiceModel> findAllRoles() {
		return this.roleRepository.findAll().stream().map(r -> this.modelMapper.map(r, RoleServiceModel.class)).collect(Collectors.toSet());
	}

	@Override
	public RoleServiceModel findByAuthority(String authority) {
		Role role = this.roleRepository.findByAuthority(authority);
		RoleServiceModel roleServiceModel = this.modelMapper.map(role, RoleServiceModel.class);

		return roleServiceModel;
	}

	@Override
	public void deleteRole(RoleServiceModel roleServiceModel) {
		Role role = this.roleRepository.findById(roleServiceModel.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		role.setStatus(StatusValues.INACTIVE);

		this.roleRepository.save(role);

	}
}
