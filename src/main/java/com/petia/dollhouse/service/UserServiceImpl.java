package com.petia.dollhouse.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.Role;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.RoleNames;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.RoleServiceModel;
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
	private final OfficeService officeService;
	private final DollHouseService dollHouseService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ValidationUtil validationUtil;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
	    ValidationUtil validationUtil, OfficeService officeService, DollHouseService dollHouseService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.modelMapper = modelMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.validationUtil = validationUtil;
		this.officeService = officeService;
		this.dollHouseService = dollHouseService;
	}

	@Override
	public UserServiceModel registerUser(UserServiceModel userServiceModel) {
		if (!this.validationUtil.isValid(userServiceModel)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		this.roleService.seedRoles();
//		if (this.userRepository.count() == 0) {
//			userServiceModel.setAuthorities(this.roleService.findAllRoles());
//
//		} else {
//			userServiceModel.setAuthorities(new HashSet<>());
//			userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));
//		}

		// User user = mapServiceToEntityModel(userServiceModel);
		User user = mapServiceToEntityModel(new User(), userServiceModel);
//		user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
//		user.setStatus(StatusValues.ACTIVE);

		if (this.userRepository.findByUsername(user.getUsername()).orElse(null) != null) {
			throw new IllegalArgumentException(Constants.EXIST_USERNAME_ERROR_MESSAGE);
		}
		user = this.userRepository.saveAndFlush(user);

//		UserServiceModel savedUser = this.modelMapper.map(user, UserServiceModel.class);
		userServiceModel = this.mapEntityToServiceModel(user, userServiceModel);

		return userServiceModel;
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
		// UserServiceModel model = this.modelMapper.map(user, UserServiceModel.class);
		UserServiceModel model = this.mapEntityToServiceModel(user, new UserServiceModel());

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

//		model.setAuthorities(new HashSet<>());
//		model.getAuthorities().add(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()));

		// User employee = this.modelMapper.map(model, User.class);
		User employee = this.mapServiceToEntityModel(new User(), model);
//		employee.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));
//		employee.setStatus(StatusValues.ACTIVE);

		if (this.userRepository.findByUsername(employee.getUsername()).orElse(null) != null) {

			throw new IllegalArgumentException(Constants.EXIST_USERNAME_ERROR_MESSAGE);
		}
		employee = this.userRepository.saveAndFlush(employee);

		String result = employee.getId();

		return result;
	}

	// TODO
	@Override
	public UserServiceModel editEmployee(UserServiceModel model) {
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
//		model.setStatus(employee.getStatus().name());
//		model.setPassword(employee.getPassword());

//		if (model.getImageUrl() == null && employee.getImageUrl() != null) {
//			model.setImageUrl(employee.getImageUrl());
//		}

		// employee = this.modelMapper.map(model, User.class);
		employee = this.mapServiceToEntityModel(employee, model);
		// employee.setAuthorities(employee.getAuthorities());

		employee = this.userRepository.save(employee);

		// model = this.modelMapper.map(employee, UserServiceModel.class);
		model = this.mapEntityToServiceModel(employee, model);

		return model;
	}

	@Override
	public UserServiceModel deleteEmployee(UserServiceModel model) {
		User employee = this.userRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		employee.setStatus(StatusValues.INACTIVE);

		employee = this.userRepository.saveAndFlush(employee);

		// model = this.modelMapper.map(employee, UserServiceModel.class);
		model = this.mapEntityToServiceModel(employee, model);

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

	@Override
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
	public User mapServiceToEntityModel(User entity, UserServiceModel model) {
		if (model.getStatus() != null) {
			entity.setStatus(StatusValues.valueOf(model.getStatus()));
		} else {
			entity.setStatus(StatusValues.ACTIVE);

		}
		if (model.getPassword() != null) {
			entity.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));
		}

		if (model.getEmail() != null) {
			entity.setEmail(model.getEmail());
		}
		if (model.getFirstName() != null) {
			entity.setFirstName(model.getFirstName());
		}

		if (model.getLastName() != null) {
			entity.setLastName(model.getLastName());
		}

		if (model.getUsername() != null) {
			entity.setUsername(model.getUsername());
		}

		if (model.getPhoneNumber() != null) {
			entity.setPhoneNumber(model.getPhoneNumber());
		}

		if (model.getImageUrl() != null) {
			entity.setImageUrl(model.getImageUrl());
		}

		if (this.userRepository.count() == 0) {
			Set<Role> roles = this.roleService.findAllRoles().stream().map(rm -> this.modelMapper.map(rm, Role.class)).collect(Collectors.toSet());
			entity.setAuthorities(roles);

		} else {
			entity.setAuthorities(new HashSet<Role>());
			Role role = this.modelMapper.map(this.roleService.findByAuthority(RoleNames.ROLE_USER.toString()), Role.class);
			entity.getAuthorities().add(role);
		}

		if (model.getOfficeServiceModel() != null)

		{
			Office office = new Office();
			office.setId(model.getOfficeServiceModel().getId());

			entity.setOffice(office);
		}
		if (model.getServiceModel() != null) {
			DHService service = new DHService();
			service.setId(model.getServiceModel().getId());

			entity.setService(service);

		}

		return entity;

	}

	@Override
	public UserServiceModel mapEntityToServiceModel(User entity, UserServiceModel model) {
		model.setId(entity.getId());

		model.setStatus(entity.getStatus().name());

		model.setPassword(entity.getPassword());

		model.setEmail(entity.getEmail());

		model.setFirstName(entity.getFirstName());

		model.setLastName(entity.getLastName());

		model.setUsername(entity.getUsername());

		model.setPhoneNumber(entity.getPhoneNumber());

		model.setImageUrl(entity.getImageUrl());

		Set<RoleServiceModel> roles = entity.getAuthorities().stream().map(r -> this.modelMapper.map(r, RoleServiceModel.class)).collect(Collectors.toSet());
		model.setAuthorities(roles);

		if (entity.getOffice() != null) {
			OfficeServiceModel officeModel = this.officeService.findOfficeByID(entity.getOffice().getId());
			model.setOfficeServiceModel(officeModel);
		}

		if (entity.getService() != null) {
			ServiceModel serviceModel = this.dollHouseService.findByID(entity.getService().getId());
			model.setServiceModel(serviceModel);
		}
		return model;
	}

	@Override
	public NamesViewModel mapUserServiceModelToNameViewModel(UserServiceModel model) {
		NamesViewModel result = new NamesViewModel();
		result.setId(model.getId());
		result.setName(model.getFirstName() + " " + model.getLastName());
		return result;
	}

}