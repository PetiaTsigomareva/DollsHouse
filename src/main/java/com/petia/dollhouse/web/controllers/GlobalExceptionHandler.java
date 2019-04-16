package com.petia.dollhouse.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.error.NotFoundExceptions;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

	@ExceptionHandler({ NotFoundExceptions.class })
	public ModelAndView handleProductNotFound(RuntimeException e) {
		ModelAndView modelAndView = new ModelAndView(Constants.ERROR_NOT_FOUND_PAGE);
		modelAndView.addObject("error-message", e.getMessage());

		return modelAndView;
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ModelAndView handleSqlException(RuntimeException e) {
		ModelAndView modelAndView = new ModelAndView(Constants.ERROR_GENERAL_PAGE);

		modelAndView.addObject(Constants.ERROR_MESSAGE_TITLE, e.getMessage());

		return modelAndView;
	}
}