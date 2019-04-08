package com.petia.dollhouse.web.controllers;

import java.security.Principal;

import com.petia.dollhouse.web.annotations.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;

@Controller
public class HomeController extends BaseController {

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
