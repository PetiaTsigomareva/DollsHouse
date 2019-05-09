package com.petia.dollhouse.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.petia.dollhouse.domain.binding.EmployeeBindingModel;
import com.petia.dollhouse.domain.binding.EmployeeEditBindingModel;
import com.petia.dollhouse.domain.binding.UserRegisterBindingModel;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;

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

	List<UserServiceModel> findEmployeesByService(String serviceId);

	UserServiceModel mapBindingToServiceModel(EmployeeBindingModel model);

	NamesViewModel mapUserServiceModelToNameViewModel(UserServiceModel model);

	UserServiceModel mapBindingToServiceModel(EmployeeEditBindingModel model);

	UserServiceModel mapBindingToServiceModel(UserRegisterBindingModel model);

	User mapServiceToEntityModel(UserServiceModel model);

	User mapServiceToEntityModel(User entity, UserServiceModel model);

	UserServiceModel mapEntityToServiceModel(User entity, UserServiceModel model);
}
