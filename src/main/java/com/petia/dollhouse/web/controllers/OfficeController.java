package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.OfficeBindingModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OfficeController extends BaseController {

    @GetMapping(Constants.ADD_OFFICE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addOffice(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel) {
        return view(Constants.ADD_OFFICE_PAGE, modelAndView);
    }
}
