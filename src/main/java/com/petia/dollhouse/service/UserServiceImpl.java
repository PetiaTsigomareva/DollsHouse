package com.petia.dollhouse.service;

import java.util.ArrayList;
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
import com.petia.dollhouse.domain.binding.EmployeeEditBindingModel;
import com.petia.dollhouse.domain.binding.UserRegisterBindingModel;
import com.petia.dollhouse.domain.entities.Role;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.repositories.UserRepository;
import com.petia.dollhouse.validation.ValidationUtil;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleService roleService;
	private final ModelMapper modelMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ValidationUtil validationUtil;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
	    ValidationUtil validationUtil) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.modelMapper = modelMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.validationUtil = validationUtil;
	}

	@Override
	public UserServiceModel registerUser(UserServiceModel userServiceModel) {
		if (!this.validationUtil.isValid(userServiceModel)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		UserServiceModel savedUser;
		this.roleService.seedRoles();
		if (this.userRepository.count() == 0) {
			userServiceModel.setAuthorities(this.roleService.findAllRoles());

		} else {
			userServiceModel.setAuthorities(new HashSet<>());
			userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
		}

		User user = mapServiceToEntityModel(userServiceModel);
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
		return this.userRepository.findByUsername(username).map(u -> this.modelMapper.map(u, UserServiceModel.class))
		    .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_ERROR_MESSAGE));
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
//		if (!this.validationUtil.isValid(userServiceModel)) {
//			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
//		}

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
		default:
			throw new IllegalArgumentException(Constants.INVALID_ROLE);

		}

		User userWithNewRole = this.modelMapper.map(userServiceModel, User.class);
		userWithNewRole.setOffice(user.getOffice());
		userWithNewRole.setService(user.getService());

		this.userRepository.saveAndFlush(userWithNewRole);

	}

	@Override
	public String addEmployee(UserServiceModel model) {
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		String result;
		model.setAuthorities(new HashSet<>());
		model.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));

		User employee = this.modelMapper.map(model, User.class);
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
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		model.setStatus(employee.getStatus().name());
		model.setPassword(employee.getPassword());

		if (model.getImageUrl() == null && employee.getImageUrl() != null) {
			model.setImageUrl(employee.getImageUrl());
		}

		employee = this.modelMapper.map(model, User.class);
		employee.setAuthorities(employee.getAuthorities());
		employee = this.userRepository.save(employee);

		model = this.modelMapper.map(employee, UserServiceModel.class);

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
		result.setUsername(model.getUsername());
		result.setLastName(model.getLastName());
		result.setPassword(model.getPassword());
		result.setPhoneNumber(model.getPhoneNumber());

		OfficeServiceModel officeServiceModel = new OfficeServiceModel();
		officeServiceModel.setId(model.getOfficeId());
		result.setOfficeServiceModel(officeServiceModel);

		ServiceModel serviceModel = new ServiceModel();
		serviceModel.setId(model.getServiceId());
		result.setServiceModel(serviceModel);

		return result;
	}

	@Override
	public UserServiceModel mapBindingToServiceModel(EmployeeEditBindingModel model) {
		UserServiceModel result = new UserServiceModel();
		result.setEmail(model.getEmail());
		result.setFirstName(model.getFirstName());
		result.setUsername(model.getUsername());
		result.setLastName(model.getLastName());
		result.setPassword(model.getPassword());
		result.setPhoneNumber(model.getPhoneNumber());

		OfficeServiceModel officeServiceModel = new OfficeServiceModel();
		officeServiceModel.setId(model.getOfficeId());
		result.setOfficeServiceModel(officeServiceModel);

		ServiceModel serviceModel = new ServiceModel();
		serviceModel.setId(model.getServiceId());
		result.setServiceModel(serviceModel);

		return result;
	}

	@Override
	public UserServiceModel mapBindingToServiceModel(UserRegisterBindingModel model) {
		UserServiceModel result = new UserServiceModel();

		result.setEmail(model.getEmail());
		result.setFirstName(model.getFirstName());
		result.setUsername(model.getUsername());
		result.setLastName(model.getLastName());
		result.setPassword(model.getPassword());
		result.setPhoneNumber(model.getPhoneNumber());

		return result;
	}

	public User mapServiceToEntityModel(UserServiceModel model) {
		User result = new User();

		result.setEmail(model.getEmail());
		result.setFirstName(model.getFirstName());
		result.setUsername(model.getUsername());
		result.setLastName(model.getLastName());
		result.setPassword(model.getPassword());
		result.setPhoneNumber(model.getPhoneNumber());
		result.setAuthorities(model.getAuthorities().stream().map(c -> this.modelMapper.map(c, Role.class)).collect(Collectors.toSet()));

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