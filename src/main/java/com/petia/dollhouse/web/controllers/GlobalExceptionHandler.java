package com.petia.dollhouse.web.controllers;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.error.NotFoundExceptions;

import javax.persistence.NonUniqueResultException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({NotFoundExceptions.class})
    public ModelAndView handleProductNotFound(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView(Constants.ERROR_GENERAL_PAGE);
        modelAndView.addObject(Constants.ERROR_MESSAGE_TITLE, e.getMessage());

        return modelAndView;
    }
//TODO validations
    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class,UsernameNotFoundException.class, NonUniqueResultException.class})
    public ModelAndView handleSqlException(RuntimeException e) {

        ModelAndView modelAndView = new ModelAndView(Constants.ERROR_GENERAL_PAGE);

        modelAndView.addObject(Constants.ERROR_MESSAGE_TITLE, e.getMessage());

        return modelAndView;
    }
}