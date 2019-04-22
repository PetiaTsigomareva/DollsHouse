package com.petia.dollhouse.web.controllers;

import java.util.NoSuchElementException;

import javax.persistence.NonUniqueResultException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.error.NotFoundExceptions;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

	@ExceptionHandler({ NotFoundExceptions.class })
	public ModelAndView handleProductNotFound(RuntimeException e) {
		e.printStackTrace();

		ModelAndView modelAndView = new ModelAndView(Constants.ERROR_GENERAL_PAGE);

		modelAndView.addObject(Constants.ERROR_MESSAGE_TITLE, e.getMessage());

		return modelAndView;
	}

	@ExceptionHandler({ IllegalArgumentException.class, NoSuchElementException.class, UsernameNotFoundException.class, NonUniqueResultException.class })
	public ModelAndView handleSqlException(RuntimeException e) {
		e.printStackTrace();

		ModelAndView modelAndView = new ModelAndView(Constants.ERROR_GENERAL_PAGE);

		modelAndView.addObject(Constants.ERROR_MESSAGE_TITLE, e.getMessage());

		return modelAndView;
	}
}