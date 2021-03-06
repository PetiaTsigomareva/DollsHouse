package com.petia.dollhouse.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.OfficeBindingModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.view.AllOfficesViewModel;
import com.petia.dollhouse.service.CompanyService;
import com.petia.dollhouse.service.OfficeService;
import com.petia.dollhouse.web.annotations.PageTitle;

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
	@PageTitle(Constants.ADD_OFFICE_TITLE)
	public ModelAndView addOffice(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel) {
		modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());
		return view(Constants.ADD_OFFICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_OFFICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addOfficeConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel,
	    BindingResult bindingResult) {
		if (bindingResult.hasErrors() || "Please select...".equals(officeBindingModel.getCompanyId())) {
			modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());
			return view(Constants.ADD_OFFICE_PAGE, modelAndView);
		}

		modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());

		this.officeService.addOffice(this.modelMapper.map(officeBindingModel, OfficeServiceModel.class));

		return redirect(Constants.ALL_OFFICE_PAGE);
	}

	@GetMapping(Constants.ALL_OFFICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ALL_OFFICE_TITLE)
	public ModelAndView allOffices(ModelAndView modelAndView) {
		List<OfficeServiceModel> officeServiceModels = this.officeService.findAllOffices();
		List<AllOfficesViewModel> allOfficesViewModels = officeServiceModels.stream().map(csm -> this.modelMapper.map(csm, AllOfficesViewModel.class)).collect(Collectors.toList());
		modelAndView.addObject("offices", allOfficesViewModels);

		return view(Constants.ALL_OFFICE_PAGE, modelAndView);
	}

	@GetMapping(Constants.EDIT_OFFICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.EDIT_OFFICE_TITLE)
	public ModelAndView editOffice(ModelAndView modelAndView, @PathVariable String id) {
		OfficeBindingModel officeBindingModel = this.modelMapper.map(this.officeService.findOfficeByID(id), OfficeBindingModel.class);
		modelAndView.addObject("companyId", officeBindingModel.getCompanyId());
		modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());
		modelAndView.addObject("bindingModel", officeBindingModel);

		return view(Constants.EDIT_OFFICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.EDIT_OFFICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView editOfficeConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel, BindingResult bindingResult,
	    @PathVariable String id) {
		if (bindingResult.hasErrors() || "Please select...".equals(officeBindingModel.getCompanyId())) {
			modelAndView.addObject("companyId", officeBindingModel.getCompanyId());
			modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());
			modelAndView.addObject("bindingModel", officeBindingModel);

			return view(Constants.EDIT_OFFICE_PAGE, modelAndView);
		}

		OfficeServiceModel officeServiceModel = this.modelMapper.map(officeBindingModel, OfficeServiceModel.class);
		officeServiceModel.setId(id);
		officeServiceModel = this.officeService.editOffice(officeServiceModel);

		return redirect(Constants.ALL_OFFICE_PAGE);
	}

	@GetMapping(Constants.DELETE_OFFICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.DELETE_OFFICE_TITLE)
	public ModelAndView deleteOffice(ModelAndView modelAndView, @PathVariable String id) {
		OfficeBindingModel officeBindingModel = this.modelMapper.map(this.officeService.findOfficeByID(id), OfficeBindingModel.class);
		modelAndView.addObject("bindingModel", officeBindingModel);
		modelAndView.addObject("companyNames", this.companyService.mapCompanyNames());
		return view(Constants.DELETE_OFFICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.DELETE_OFFICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteOfficeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") OfficeBindingModel officeBindingModel, @PathVariable String id) {
		OfficeServiceModel officeServiceModel = this.modelMapper.map(officeBindingModel, OfficeServiceModel.class);
		officeServiceModel.setId(id);
		officeServiceModel = this.officeService.deleteOffice(officeServiceModel);

		return redirect(Constants.ALL_OFFICE_PAGE);
	}
}