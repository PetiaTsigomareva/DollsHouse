package com.petia.dollhouse.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.EmployeeBindingModel;
import com.petia.dollhouse.domain.binding.EmployeeEditBindingModel;
import com.petia.dollhouse.domain.binding.UserEditBindingModel;
import com.petia.dollhouse.domain.binding.UserRegisterBindingModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.AllUserViewModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.domain.view.UserProfileViewModel;
import com.petia.dollhouse.service.CloudinaryService;
import com.petia.dollhouse.service.DollHouseService;
import com.petia.dollhouse.service.OfficeService;
import com.petia.dollhouse.service.UserService;
import com.petia.dollhouse.web.annotations.PageTitle;

@Controller
public class UserController extends BaseController {
	private final DollHouseService dollHouseService;
	private final UserService userService;
	private final OfficeService officeService;
	private final ModelMapper modelMapper;
	private final CloudinaryService cloudinaryService;

	@Autowired
	public UserController(DollHouseService dollHouseService, UserService userService, OfficeService officeService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
		this.dollHouseService = dollHouseService;
		this.userService = userService;
		this.officeService = officeService;
		this.modelMapper = modelMapper;
		this.cloudinaryService = cloudinaryService;
	}

	@GetMapping(Constants.REGISTER_FORM_ACTION)
	@PreAuthorize("isAnonymous()")
	@PageTitle(Constants.REGISTER_TITLE)
	public ModelAndView register() {
		return super.view(Constants.REGISTER_PAGE);
	}

	@PostMapping(Constants.REGISTER_FORM_ACTION)
	@PreAuthorize("isAnonymous()")
	public ModelAndView registerConfirm(@Valid @ModelAttribute UserRegisterBindingModel model) {
		if (!model.getPassword().equals(model.getConfirmPassword())) {
			return view(Constants.REGISTER_PAGE);
		}

		this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));

		return redirect(Constants.LOGIN_FORM_ACTION);
	}

	@GetMapping(Constants.LOGIN_FORM_ACTION)
	@PreAuthorize("isAnonymous()")
	@PageTitle(Constants.LOGIN_TITLE)
	public ModelAndView login() {
		return view(Constants.LOGIN_PAGE);
	}

	@GetMapping(Constants.PROFILE_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.PROFILE_TITLE)
	public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
		modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));

		return view(Constants.PROFILE_PAGE, modelAndView);
	}

	@GetMapping(Constants.EDIT_PROFILE_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.EDIT_PROFILE_TITLE)
	public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
		modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), UserEditBindingModel.class));

		return view(Constants.EDIT_PROFILE_PAGE, modelAndView);
	}

	@PatchMapping(Constants.EDIT_PROFILE_ACTION)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) throws IOException {
		if (!model.getPassword().equals(model.getConfirmPassword())) {
			return super.view(Constants.EDIT_PROFILE_ACTION);
		}
		UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
		if (!model.getImage().isEmpty()) {
			userServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
		}
		this.userService.editUserProfile(userServiceModel, model.getOldPassword());

		return redirect(Constants.PROFILE_ACTION);
	}

	@GetMapping(Constants.ADMIN_SIDEBAR_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.ADMIN_SIDEBAR_TITLE)
	public ModelAndView adminSideBar(ModelAndView modelAndView) {

		return super.view(Constants.ADMIN_SIDEBAR_PAGE, modelAndView);
	}

	@GetMapping(Constants.MODERATOR_SIDEBAR_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.MODERATOR_SIDEBAR_TITLE)
	public ModelAndView moderatorSideBar(ModelAndView modelAndView) {

		return super.view(Constants.MODERATOR_SIDEBAR_PAGE, modelAndView);
	}

	@GetMapping(Constants.ADD_EMPLOYEE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ADD_EMPLOYEE_TITLE)
	public ModelAndView addEmployee(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel) {
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		return view(Constants.ADD_EMPLOYEE_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_EMPLOYEE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addEmployeesConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel,
	    BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
			return view(Constants.ADD_EMPLOYEE_PAGE, modelAndView);
		}

		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		UserServiceModel userServiceModel = this.userService.mapBindingToServiceModel(employeeBindingModel);
		if (!employeeBindingModel.getImage().isEmpty()) {
			userServiceModel.setImageUrl(this.cloudinaryService.uploadImage(employeeBindingModel.getImage()));
		}

		String id = this.userService.addEmployee(userServiceModel);
		if (id == null) {
			return view(Constants.ADD_EMPLOYEE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_EMPLOYEES_PAGE);
	}

	@GetMapping(Constants.EDIT_EMPLOYEE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.EDIT_EMPLOYEE_TITLE)
	public ModelAndView editEmployee(ModelAndView modelAndView, @PathVariable String id) {
		EmployeeEditBindingModel employeeBindingModel = this.modelMapper.map(this.userService.findUserById(id), EmployeeEditBindingModel.class);
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.getAllServicesByOffice(employeeBindingModel.getOfficeId()));
		modelAndView.addObject("bindingModel", employeeBindingModel);

		return view(Constants.EDIT_EMPLOYEE_PAGE, modelAndView);
	}

	// TODO
	@PostMapping(Constants.EDIT_EMPLOYEE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView editEmployeeConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") EmployeeEditBindingModel employeeBindingModel,
	    BindingResult bindingResult, @PathVariable String id) throws IOException {
		if (bindingResult.hasErrors() || "Please select...".equals(employeeBindingModel.getOfficeId()) || "Please select...".equals(employeeBindingModel.getServiceId())) {
			modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
			modelAndView.addObject("serviceNames", this.dollHouseService.getAllServicesByOffice(employeeBindingModel.getOfficeId()));
			modelAndView.addObject("bindingModel", employeeBindingModel);

			return view(Constants.EDIT_EMPLOYEE_PAGE, modelAndView);
		}

		UserServiceModel userServiceModel = this.userService.mapBindingToServiceModel(employeeBindingModel);
		userServiceModel.setId(id);
		if (!employeeBindingModel.getImage().isEmpty()) {
			userServiceModel.setImageUrl(this.cloudinaryService.uploadImage(employeeBindingModel.getImage()));
		}
		userServiceModel = this.userService.editEmployee(userServiceModel);

		if (userServiceModel == null) {
			return view(Constants.EDIT_EMPLOYEE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_EMPLOYEES_PAGE);
	}

	@GetMapping(Constants.DELETE_EMPLOYEE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.DELETE_EMPLOYEE_TITLE)
	public ModelAndView deleteEmployee(ModelAndView modelAndView, @PathVariable String id) {

		EmployeeBindingModel employeeBindingModel = this.modelMapper.map(this.userService.findUserById(id), EmployeeBindingModel.class);
		modelAndView.addObject("bindingModel", employeeBindingModel);
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		return view(Constants.DELETE_EMPLOYEE_PAGE, modelAndView);
	}

	@PostMapping(Constants.DELETE_EMPLOYEE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteEmployeeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel, @PathVariable String id) {

		UserServiceModel userServiceModel = this.userService.mapBindingToServiceModel(employeeBindingModel);
		userServiceModel.setId(id);
		userServiceModel = this.userService.deleteEmployee(userServiceModel);
		if (userServiceModel == null) {

			return view(Constants.DELETE_EMPLOYEE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_EMPLOYEES_PAGE);
	}

	@GetMapping(Constants.AlL_EMLOYEES_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ALL_EMPLOYEES_TITLE)
	public ModelAndView allEmployees(ModelAndView modelAndView) {
		List<AllUserViewModel> users = this.userService.findUsersByCriteria(Constants.EMPLOYEE).stream().map(u -> {
			AllUserViewModel user = this.modelMapper.map(u, AllUserViewModel.class);
			user.setAuthorities(u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()));

			return user;
		}).collect(Collectors.toList());

		modelAndView.addObject("users", users);

		return view(Constants.ALL_EMPLOYEES_PAGE, modelAndView);
	}

	@PostMapping(Constants.SET_USER_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView setUser(@PathVariable String id) {
		this.userService.setUserRole(id, Constants.USER);

		return redirect(Constants.AlL_EMLOYEES_ACTION);
	}

	@PostMapping(Constants.SET_MODERATOR_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView setModerator(@PathVariable String id) {
		this.userService.setUserRole(id, Constants.MODERATOR);

		return redirect(Constants.AlL_EMLOYEES_ACTION);
	}

	@PostMapping(Constants.SET_ADMIN_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView setAdmin(@PathVariable String id) {
		this.userService.setUserRole(id, Constants.ADMIN);

		return redirect(Constants.AlL_EMLOYEES_ACTION);
	}

	@GetMapping(Constants.FETCH_SERVICE_ALL_EMPLOYEES_ACTION + "{serviceId}")
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public List<NamesViewModel> fetchEmployeesByService(@PathVariable String serviceId) {
		List<NamesViewModel> result;

		result = this.userService.findEmployeesByService(serviceId).stream().map(product -> this.userService.mapUserServiceModelToNameViewModel(product)).collect(Collectors.toList());

		return result;
	}
}
