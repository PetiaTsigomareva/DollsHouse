package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.CompanyBindingModel;
import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.view.CompanyAllViewModel;
import com.petia.dollhouse.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CompanyController extends BaseController {
    public final static String OWNER_NAME = "Joanna Masheva";
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.ADD_COMPANY_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCompany(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") CompanyBindingModel companyBindingModel) {
        return view(Constants.ADD_COMPANY_PAGE, modelAndView);
    }

    @PostMapping(Constants.ADD_COMPANY_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCompanyConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") CompanyBindingModel companyBindingModel) {
        modelAndView.addObject("bindingModel", companyBindingModel);

        String id = this.companyService.addCompany(this.modelMapper.map(companyBindingModel, CompanyServiceModel.class));
        if (id == null) {
            return view(Constants.ADD_COMPANY_PAGE, modelAndView);
        }

        return redirect(Constants.HOME_ACTION);
    }

    @GetMapping(Constants.ALL_COMPANY_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allCompanies(ModelAndView modelAndView) {
        List<CompanyServiceModel> companyServiceModels = this.companyService.findAllCompanies();
        List<CompanyAllViewModel> companyAllViewModels = companyServiceModels.stream().map(csm -> this.modelMapper.map(csm, CompanyAllViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("companies", companyAllViewModels);

        return view(Constants.ALL_COMPANY_PAGE, modelAndView);
    }


    @GetMapping(Constants.EDIT_COMPANY_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCompany(ModelAndView modelAndView, @PathVariable String id) {

        CompanyBindingModel companyBindingModel = this.modelMapper.map(this.companyService.findCompanyByID(id), CompanyBindingModel.class);
        modelAndView.addObject("bindingModel", companyBindingModel);

        return view(Constants.EDIT_COMPANY_PAGE, modelAndView);
    }

    @PostMapping(Constants.EDIT_COMPANY_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCompanyConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") CompanyBindingModel companyBindingModel, @PathVariable String id) {

        CompanyServiceModel companyServiceModel = this.modelMapper.map(companyBindingModel, CompanyServiceModel.class);
        companyServiceModel.setId(id);
        companyServiceModel = this.companyService.editCompanyInfo(companyServiceModel);
        if (companyServiceModel == null) {

            return view(Constants.EDIT_COMPANY_PAGE, modelAndView);
        }

        return redirect(Constants.HOME_ACTION);


    }

    @GetMapping(Constants.DELETE_COMPANY_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCompany(ModelAndView modelAndView, @PathVariable String id) {

        CompanyBindingModel companyBindingModel = this.modelMapper.map(this.companyService.findCompanyByID(id), CompanyBindingModel.class);
        modelAndView.addObject("bindingModel", companyBindingModel);

        return view(Constants.DELETE_COMPANY_PAGE, modelAndView);
    }

    @PostMapping(Constants.DELETE_COMPANY_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCompanyConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") CompanyBindingModel companyBindingModel, @PathVariable String id) {

        CompanyServiceModel companyServiceModel = this.modelMapper.map(companyBindingModel, CompanyServiceModel.class);
        companyServiceModel.setId(id);
        companyServiceModel = this.companyService.deleteCompany(companyServiceModel);
        if (companyServiceModel == null) {

            return view(Constants.DELETE_COMPANY_PAGE, modelAndView);
        }

        return redirect(Constants.HOME_ACTION);


    }


}
