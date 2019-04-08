package com.petia.dollhouse.service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.Positions;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final OfficeService officeService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, OfficeService officeService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.officeService = officeService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //TODO refactoring  set admin authority and set office of the admin
    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        UserServiceModel savedUser;
        this.roleService.seedRoles();
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
            userServiceModel.setPosition(Positions.root_admin.toString());
        } else {
            userServiceModel.setAuthorities(new HashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
        }


        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setStatus(StatusValues.ACTIVE);


        savedUser = this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_ERROR_MESSAGE));
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username).map(u -> this.modelMapper.map(u, UserServiceModel.class)).orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_ERROR_MESSAGE));
    }

    @Override
    public UserServiceModel findUserById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
        UserServiceModel model = this.modelMapper.map(user, UserServiceModel.class);

        return model;
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_ERROR_MESSAGE));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(Constants.PASSWORD_ERROR_MESSAGE);
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ? this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) : user.getPassword());
        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    //TODO optimization
    @Override
    public List<UserServiceModel> findAllUsers() {
        // return this.userRepository.findAll().stream().map(u -> this.modelMapper.map(u, UserServiceModel.class)).collect(Collectors.toList());
        return this.userRepository.findAllEmployees().stream().map(u -> this.modelMapper.map(u, UserServiceModel.class)).collect(Collectors.toList());
    }

    //TODO optimization - set user roles implementation
    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(Constants.ID_ERROR_MESSAGE));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
                break;
            case "moderator":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_MODERATOR.toString()));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_MODERATOR.toString()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_ADMIN.toString()));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public String addEmployee(UserServiceModel model) {
        String result;
        try {

            model.setAuthorities(new HashSet<>());
            model.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));

            User employee = this.modelMapper.map(model, User.class);
            employee.setOffice(findOffice(model.getOfficeId()));
            employee.setStatus(StatusValues.ACTIVE);
            employee.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));

            employee = this.userRepository.saveAndFlush(employee);
            result = employee.getId();

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = null;
        }

        return result;
    }

    //TODO
    @Override
    public UserServiceModel editEmployee(UserServiceModel model) {

        User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
        model.setStatus(employee.getStatus().name());
        model.setPassword(employee.getPassword());

        User employeeNew = this.modelMapper.map(model, User.class);
        employeeNew.setOffice(findOffice(model.getOfficeId()));
        User mappedUser = this.userRepository.saveAndFlush(employeeNew);

        model = this.modelMapper.map(mappedUser, UserServiceModel.class);

        return model;
    }

    @Override
    public UserServiceModel deleteEmployee(UserServiceModel model) {
        User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
        employee.setStatus(StatusValues.INACTIVE);

        employee = this.userRepository.saveAndFlush(employee);

        model = this.modelMapper.map(employee, UserServiceModel.class);

        return model;
    }

    private Office findOffice(String id) {
        Office office;
        office = this.modelMapper.map(this.officeService.findOfficeByID(id), Office.class);

        return office;

    }


}
