package com.petia.dollhouse.web.controllers;

import java.security.Principal;
import java.util.List;

import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.AboutViewModel;
import com.petia.dollhouse.domain.view.AllEmployeeViewModel;
import com.petia.dollhouse.service.CompanyService;
import com.petia.dollhouse.service.UserService;
import com.petia.dollhouse.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;

@Controller
public class HomeController extends BaseController {

    private final CompanyService companyService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CompanyService companyService, UserService userService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.INDEX_ACTION)
    @PageTitle(Constants.INDEX_TITLE)
    public ModelAndView index(ModelAndView modelAndView) {

        return super.view(Constants.INDEX_PAGE, modelAndView);

    }

    @GetMapping(Constants.HOME_ACTION)
    @PreAuthorize("isAuthenticated()")
    @PageTitle(Constants.HOME_TITLE)
    public ModelAndView home(ModelAndView modelAndView) {

        return super.view(Constants.INDEX_PAGE, modelAndView);
    }

    @GetMapping(Constants.ABOUT_ACTION)
    @PageTitle(Constants.ABOUT_TITLE)
    public ModelAndView about(ModelAndView modelAndView) {
        return super.view(Constants.ABOUT_PAGE, modelAndView);

    }

    @GetMapping(Constants.CONTACT_ACTION)
    @PageTitle(Constants.CONTACT_TITLE)
    public ModelAndView contact(ModelAndView modelAndView) {

        return super.view(Constants.CONTACT_PAGE, modelAndView);

    }

}
