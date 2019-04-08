package com.petia.dollhouse.service;


import com.petia.dollhouse.domain.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel findUserById(String id);

    List<UserServiceModel> findUsersByOfficeId(String id);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);

    String addEmployee(UserServiceModel model);

    UserServiceModel editEmployee(UserServiceModel model);

    UserServiceModel deleteEmployee(UserServiceModel companyServiceModel);
}
