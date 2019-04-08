package com.petia.dollhouse.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.petia.dollhouse.domain.view.AllCompanyViewModel;
import com.petia.dollhouse.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.CompanyBindingModel;
import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.service.CompanyService;

@Controller
public class CompanyController extends BaseController {

	private final CompanyService companyService;
	private final ModelMapper modelMapper;

	@Autowired
	public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
		this.companyService = companyService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(Constants.ADD_COMPANY_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ADD_COMPANY_TITLE)
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
		return redirect(Constants.ALL_COMPANY_PAGE);
	}

	@GetMapping(Constants.ALL_COMPANY_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ALL_COMPANY_TITLE)
	public ModelAndView allCompanies(ModelAndView modelAndView) {
		List<CompanyServiceModel> companyServiceModels = this.companyService.findAllCompanies();
		List<AllCompanyViewModel> allCompanyViewModels = companyServiceModels.stream().map(csm -> this.modelMapper.map(csm, AllCompanyViewModel.class)).collect(Collectors.toList());
		modelAndView.addObject("companies", allCompanyViewModels);

		return view(Constants.ALL_COMPANY_PAGE, modelAndView);
	}

	@GetMapping(Constants.EDIT_COMPANY_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.EDIT_COMPANY_TITLE)
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
		companyServiceModel = this.companyService.editCompany(companyServiceModel);
		if (companyServiceModel == null) {

			return view(Constants.EDIT_COMPANY_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_COMPANY_PAGE);

	}

	@GetMapping(Constants.DELETE_COMPANY_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.DELETE_COMPANY_TITLE)
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

		return redirect(Constants.ALL_COMPANY_PAGE);

	}

}
