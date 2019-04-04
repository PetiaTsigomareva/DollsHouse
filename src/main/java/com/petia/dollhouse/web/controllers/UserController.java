package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.EmployeeBindingModel;
import com.petia.dollhouse.domain.binding.UserEditBindingModel;
import com.petia.dollhouse.domain.binding.UserRegisterBindingModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.AllUserViewModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.domain.view.UserProfileViewModel;
import com.petia.dollhouse.service.OfficeService;
import com.petia.dollhouse.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class UserController extends BaseController {

    private final UserService userService;
    private final OfficeService officeService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, OfficeService officeService, ModelMapper modelMapper) {
        this.userService = userService;
        this.officeService = officeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.REGISTER_FORM_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view(Constants.REGISTER_PAGE);
    }

    @PostMapping(Constants.REGISTER_FORM_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return view(Constants.REGISTER_PAGE);
        }

        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));

        return redirect(Constants.LOGIN_FORM_ACTION);
    }

    @GetMapping(Constants.LOGIN_FORM_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return view(Constants.LOGIN_PAGE);
    }


    @GetMapping(Constants.PROFILE_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));

        return view(Constants.PROFILE_PAGE, modelAndView);
    }

    @GetMapping(Constants.EDIT_PROFILE_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));

        return view(Constants.EDIT_PROFILE_PAGE, modelAndView);
    }


    @PatchMapping(Constants.EDIT_PROFILE_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view(Constants.EDIT_PROFILE_ACTION);
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return redirect(Constants.PROFILE_ACTION);
    }

    @GetMapping(Constants.ADD_EMPLOYEE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addEmployee(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel) {

        modelAndView.addObject("officeNames", getOfficeNames());
        return view(Constants.ADD_EMPLOYEE_PAGE, modelAndView);

    }

    @PostMapping(Constants.ADD_EMPLOYEE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addEmployeesConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel) {
        modelAndView.addObject("officeNames", getOfficeNames());

        String id = this.userService.addEmployee(this.modelMapper.map(employeeBindingModel, UserServiceModel.class));
        if (id == null) {
            return view(Constants.ADD_EMPLOYEE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_EMPLOYEES_PAGE);
    }

    @GetMapping(Constants.EDIT_EMPLOYEE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editEmployee(ModelAndView modelAndView, @PathVariable String id) {
        EmployeeBindingModel employeeBindingModel = this.modelMapper.map(this.userService.findUserById(id), EmployeeBindingModel.class);
        modelAndView.addObject("officeId", employeeBindingModel.getOfficeId());
        modelAndView.addObject("officeNames", getOfficeNames());
        modelAndView.addObject("bindingModel", employeeBindingModel);

        return view(Constants.EDIT_EMPLOYEE_PAGE, modelAndView);
    }

    //TODO
    @PostMapping(Constants.EDIT_EMPLOYEE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editEmployeeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel, @PathVariable String id) {

        UserServiceModel userServiceModel = this.modelMapper.map(employeeBindingModel, UserServiceModel.class);
        userServiceModel.setId(id);
        userServiceModel = this.userService.editEmployee(userServiceModel);
        if (userServiceModel == null) {

            return view(Constants.EDIT_EMPLOYEE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_EMPLOYEES_PAGE);

    }

    @GetMapping(Constants.DELETE_EMPLOYEE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteEmployee(ModelAndView modelAndView, @PathVariable String id) {

        EmployeeBindingModel employeeBindingModel = this.modelMapper.map(this.userService.findUserById(id), EmployeeBindingModel.class);
        modelAndView.addObject("bindingModel", employeeBindingModel);
        modelAndView.addObject("officeId", employeeBindingModel.getOfficeId());
        modelAndView.addObject("officeNames", getOfficeNames());
        return view(Constants.DELETE_EMPLOYEE_PAGE, modelAndView);
    }

    @PostMapping(Constants.DELETE_EMPLOYEE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteEmployeeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") EmployeeBindingModel employeeBindingModel, @PathVariable String id) {

        UserServiceModel userServiceModel = this.modelMapper.map(employeeBindingModel, UserServiceModel.class);
        userServiceModel.setId(id);
        userServiceModel = this.userService.deleteEmployee(userServiceModel);
        if (userServiceModel == null) {

            return view(Constants.DELETE_EMPLOYEE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_EMPLOYEES_PAGE);

    }


    @GetMapping(Constants.AlL_EMLOYEES_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<AllUserViewModel> users = this.userService.findAllUsers().stream().map(u -> {
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
        this.userService.setUserRole(id, "user");

        return redirect(Constants.AlL_EMLOYEES_ACTION);
    }

    @PostMapping(Constants.SET_MODERATOR_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");

        return redirect(Constants.AlL_EMLOYEES_ACTION);
    }

    @PostMapping(Constants.SET_ADMIN_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");

        return redirect(Constants.AlL_EMLOYEES_ACTION);
    }

    private List<NamesViewModel> getOfficeNames() {
        List<NamesViewModel> result;
        result = this.officeService.findAllOffices().stream().map(o -> this.modelMapper.map(o, NamesViewModel.class)).collect(Collectors.toList());

        return result;

    }


}
