package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    @GetMapping(Constants.INDEX_ACTION)
    public ModelAndView index(ModelAndView modelAndView) {

        return super.view("index", modelAndView);

    }

    @GetMapping(Constants.HOME_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        modelAndView.addObject("username", username);

        return super.view("index", modelAndView);
    }

    @GetMapping(Constants.ABOUT_ACTION)
    public ModelAndView about(ModelAndView modelAndView) {

        return super.view("about", modelAndView);

    }

    @GetMapping(Constants.CONTACT_ACTION)
    public ModelAndView contact(ModelAndView modelAndView) {

        return super.view("contact", modelAndView);

    }

}
