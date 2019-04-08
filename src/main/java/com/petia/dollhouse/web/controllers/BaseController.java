package com.petia.dollhouse.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

	public ModelAndView view(String viewName, ModelAndView modelAndView) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username

		modelAndView.addObject("username", name);
		modelAndView.setViewName(viewName);

		return modelAndView;
	}

	public ModelAndView view(String viewName) {
		return this.view(viewName, new ModelAndView());
	}

	public ModelAndView redirect(String url) {
		return this.view("redirect:" + url);
	}

}
