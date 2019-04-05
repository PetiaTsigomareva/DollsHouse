package com.petia.dollhouse.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ServiceBindingModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.service.DollHouseService;
import com.petia.dollhouse.service.OfficeService;

@Controller
public class ServiceConroller extends BaseController {
	private final DollHouseService dollHouseService;
	private final OfficeService officeService;
	private final ModelMapper modelMapper;

	@Autowired
	public ServiceConroller(OfficeService officeService, ModelMapper modelMapper, DollHouseService dollHouseService) {
		super();
		this.officeService = officeService;
		this.modelMapper = modelMapper;
		this.dollHouseService = dollHouseService;
	}

	@GetMapping(Constants.ADD_SERVICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addOffice(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel) {
		modelAndView.addObject("officeNames", getOfficeNames());
		return view(Constants.ADD_SERVICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_SERVICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addOfficeConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel) {
		modelAndView.addObject("officeNames", getOfficeNames());
		String id = this.dollHouseService.add(this.modelMapper.map(serviceBindingModel, ServiceModel.class));
		if (id == null) {
			return view(Constants.ADD_SERVICE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_SERVICE_PAGE);
	}

	private List<NamesViewModel> getOfficeNames() {
		List<NamesViewModel> result;
		result = this.officeService.findAllOffices().stream().map(o -> this.modelMapper.map(o, NamesViewModel.class)).collect(Collectors.toList());

		return result;

	}

}
