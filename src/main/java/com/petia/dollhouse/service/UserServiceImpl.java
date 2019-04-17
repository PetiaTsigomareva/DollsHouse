package com.petia.dollhouse.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.EmployeeBindingModel;
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.Positions;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final OfficeService officeService;
    private final DollHouseService serviceService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, OfficeService officeService, DollHouseService serviceService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.officeService = officeService;
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // TODO refactoring set admin authority and set office of the admin
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

        if (this.userRepository.findByUsername(user.getUsername()).orElse(null) != null) {

            throw new IllegalArgumentException(Constants.EXIST_USERNAME_ERROR_MESSAGE);
        }
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
    public List<UserServiceModel> findUsersByOfficeId(String id) {
        List<UserServiceModel> result;
        List<User> employees = this.userRepository.findAllEmployeesByOffice(id);
        result = employees.stream().map(e -> this.modelMapper.map(e, UserServiceModel.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_ERROR_MESSAGE));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(Constants.PASSWORD_ERROR_MESSAGE);
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ? this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) : user.getPassword());
        user.setEmail(userServiceModel.getEmail());
        if (userServiceModel.getImageUrl() != null) {
            user.setImageUrl(userServiceModel.getImageUrl());
        }
        user = this.userRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findUsersByCriteria(String criteria) {
        List<UserServiceModel> result;
        switch (criteria) {
            case Constants.EMPLOYEE:
                result = this.userRepository.findAllEmployees().stream().map(u -> this.modelMapper.map(u, UserServiceModel.class)).collect(Collectors.toList());

                break;
            case Constants.CUSTOMER:
                result = this.userRepository.findAllCustomers().stream().map(u -> this.modelMapper.map(u, UserServiceModel.class)).collect(Collectors.toList());

                break;
            default:
                throw new IllegalArgumentException(Constants.CRITERIA_ERROR_MESSAGE);

        }
        return result;
    }

    // TODO optimization - set user roles implementation
    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(Constants.ID_ERROR_MESSAGE));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case Constants.USER:
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
                break;
            case Constants.MODERATOR:
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_MODERATOR.toString()));
                break;
            case Constants.ADMIN:
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

            model.setAuthorities(new HashSet<>());
            model.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));

            User employee = this.modelMapper.map(model, User.class);
            employee.setOffice(findOffice(model.getOfficeId()));
            employee.setEmployeeService(findService(model.getServiceId()));
            employee.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));
            employee.setStatus(StatusValues.ACTIVE);

        if (this.userRepository.findByUsername(employee.getUsername()).orElse(null) != null) {

            throw new IllegalArgumentException(Constants.EXIST_USERNAME_ERROR_MESSAGE);
        }
            employee = this.userRepository.saveAndFlush(employee);

            result = employee.getId();



        return result;
    }

    // TODO
    @Override
    public UserServiceModel editEmployee(UserServiceModel model) {

        User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
        model.setStatus(employee.getStatus().name());
        model.setPassword(employee.getPassword());

        if (model.getImageUrl() == null && employee.getImageUrl() != null) {
            model.setImageUrl(employee.getImageUrl());
        }

        User employee2 = this.modelMapper.map(model, User.class);

        employee2.setOffice(findOffice(model.getOfficeId()));

        employee2.setEmployeeService(findService(model.getServiceId()));

        employee2.setAuthorities(employee.getAuthorities());
        User storedUser = this.userRepository.saveAndFlush(employee2);

        model = this.modelMapper.map(storedUser, UserServiceModel.class);

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

    private DHService findService(String id) {
        DHService service;
        service = this.modelMapper.map(this.serviceService.findByID(id), DHService.class);
        return service;
    }

    public List<NamesViewModel> mapUserNamesByCriteria(String criteria) {
        List<NamesViewModel> result = new ArrayList<>();
        List<UserServiceModel> userServiceModels = findUsersByCriteria(criteria);
        for (UserServiceModel u : userServiceModels) {
            NamesViewModel namesViewModel = new NamesViewModel();
            namesViewModel.setId(u.getId());
            namesViewModel.setName(u.getFirstName() + " " + u.getLastName() + "(" + u.getUsername() + ")");
            result.add(namesViewModel);

        }

        return result;
    }

    @Override
    public List<UserServiceModel> findEmployeesByService(String serviceId) {
        List<User> users = this.userRepository.findAllEmployeesByService(serviceId);

        List<UserServiceModel> userServiceModel = users.stream().map(s -> this.modelMapper.map(s, UserServiceModel.class)).collect(Collectors.toList());

        return userServiceModel;
    }

    @Override
    public UserServiceModel mapBindingToServiceModel(EmployeeBindingModel model) {
        UserServiceModel result = new UserServiceModel();
        result.setEmail(model.getEmail());
        result.setFirstName(model.getFirstName());
//		result.setId(model.getid);
//		result.setImageUrl(model.getimageUrl);
        result.setUsername(model.getUsername());
        result.setLastName(model.getLastName());
        result.setPassword(model.getPassword());
        result.setPhoneNumber(model.getPhoneNumber());
        result.setOfficeId(model.getOfficeId());
        result.setServiceId(model.getServiceId());
        result.setDescription(model.getDescription());
        return result;
    }

    @Override
    public NamesViewModel mapUserServiceModelToNameViewModel(UserServiceModel model) {
        NamesViewModel result = new NamesViewModel();
        result.setId(model.getId());
        result.setName(model.getFirstName() + " " + model.getLastName());
        return result;
    }
}