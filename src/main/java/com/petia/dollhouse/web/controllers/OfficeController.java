package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.OfficeBindingModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.view.AllOfficesViewModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.service.CompanyService;
import com.petia.dollhouse.service.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OfficeController extends BaseController {

    private final CompanyService companyService;
    private final OfficeService officeService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfficeController(CompanyService companyService, OfficeService officeService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.officeService = officeService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(Constants.ADD_OFFICE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addOffice(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel) {
        modelAndView.addObject("companyNames", getCompanyNames());
        return view(Constants.ADD_OFFICE_PAGE, modelAndView);
    }

    @PostMapping(Constants.ADD_OFFICE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addOfficeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel) {
        modelAndView.addObject("companyNames", getCompanyNames());
        // modelAndView.addObject("bindingModel", officeBindingModel);
        String id = this.officeService.addOffice(this.modelMapper.map(officeBindingModel, OfficeServiceModel.class));
        if (id == null) {
            return view(Constants.ADD_OFFICE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_OFFICE_PAGE);
    }

    @GetMapping(Constants.ALL_OFFICE_ACTION)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOffices(ModelAndView modelAndView) {
        List<OfficeServiceModel> officeServiceModels = this.officeService.findAllOffices();
        List<AllOfficesViewModel> allOfficesViewModels = officeServiceModels.stream().map(csm -> this.modelMapper.map(csm, AllOfficesViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("offices", allOfficesViewModels);

        return view(Constants.ALL_OFFICE_PAGE, modelAndView);
    }


    @GetMapping(Constants.EDIT_OFFICE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editOffice(ModelAndView modelAndView, @PathVariable String id) {
        OfficeBindingModel officeBindingModel = this.modelMapper.map(this.officeService.findOfficeByID(id), OfficeBindingModel.class);
        modelAndView.addObject("companyId", officeBindingModel.getCompanyId());
        modelAndView.addObject("companyNames", getCompanyNames());
        modelAndView.addObject("bindingModel", officeBindingModel);

        return view(Constants.EDIT_OFFICE_PAGE, modelAndView);
    }


    @PostMapping(Constants.EDIT_OFFICE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editOfficeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel, @PathVariable String id) {

        OfficeServiceModel officeServiceModel = this.modelMapper.map(officeBindingModel, OfficeServiceModel.class);
        officeServiceModel.setId(id);
        officeServiceModel = this.officeService.editOffice(officeServiceModel);
        if (officeServiceModel == null) {

            return view(Constants.EDIT_OFFICE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_OFFICE_PAGE);

    }

    @GetMapping(Constants.DELETE_OFFICE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteOffice(ModelAndView modelAndView, @PathVariable String id) {

        OfficeBindingModel officeBindingModel = this.modelMapper.map(this.officeService.findOfficeByID(id), OfficeBindingModel.class);
        modelAndView.addObject("bindingModel", officeBindingModel);
        modelAndView.addObject("companyNames", getCompanyNames());
        return view(Constants.DELETE_OFFICE_PAGE, modelAndView);
    }

    @PostMapping(Constants.DELETE_OFFICE_ACTION + "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteOfficeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel, @PathVariable String id) {

        OfficeServiceModel officeServiceModel = this.modelMapper.map(officeBindingModel, OfficeServiceModel.class);
        officeServiceModel.setId(id);
        officeServiceModel = this.officeService.deleteOffice(officeServiceModel);
        if (officeServiceModel == null) {

            return view(Constants.DELETE_OFFICE_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_OFFICE_PAGE);

    }

    private List<NamesViewModel> getCompanyNames() {
        List<NamesViewModel> result;
        result = this.companyService.findAllCompanies().stream().map(c -> this.modelMapper.map(c, NamesViewModel.class)).collect(Collectors.toList());

        return result;

    }



}
