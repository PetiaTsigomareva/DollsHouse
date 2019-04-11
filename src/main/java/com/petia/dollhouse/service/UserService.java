package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.view.NamesViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.petia.dollhouse.domain.service.UserServiceModel;

public interface UserService extends UserDetailsService {

	UserServiceModel registerUser(UserServiceModel userServiceModel);

	UserServiceModel findUserByUserName(String username);

	UserServiceModel findUserById(String id);

	List<UserServiceModel> findUsersByOfficeId(String id);

	UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

	List<UserServiceModel> findUsersByCriteria(String criteria);

	void setUserRole(String id, String role);

	String addEmployee(UserServiceModel model);

	UserServiceModel editEmployee(UserServiceModel model);

	UserServiceModel deleteEmployee(UserServiceModel companyServiceModel);

	List<NamesViewModel> mapUserNamesByCriteria(String criteria);
}
