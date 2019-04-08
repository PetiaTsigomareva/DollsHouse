package com.petia.dollhouse.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.petia.dollhouse.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ServiceBindingModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.view.AllServicesViewModel;
import com.petia.dollhouse.domain.view.AvailabilityViewModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.service.DollHouseService;
import com.petia.dollhouse.service.OfficeService;

@Controller
//@RequestMapping(Constants.SERVICE_CONTEXT)
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
	@PageTitle(Constants.ADD_SERVICE_TITLE)
	public ModelAndView addService(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel) {
		modelAndView.addObject("officeNames", getOfficeNames());
		return view(Constants.ADD_SERVICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_SERVICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addServiceConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel) {
		modelAndView.addObject("officeNames", getOfficeNames());
		String id = this.dollHouseService.add(this.modelMapper.map(serviceBindingModel, ServiceModel.class));
		if (id == null) {
			return view(Constants.ADD_SERVICE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_SERVICE_PAGE);
	}

	@GetMapping(Constants.ALL_SERVICE_ACTION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.ALL_SERVICE_TITLE)
	public ModelAndView allServices(ModelAndView modelAndView) {
		List<ServiceModel> serviceModels = this.dollHouseService.findAll();
		List<AllServicesViewModel> allServicesViewModels = serviceModels.stream().map(sm -> this.modelMapper.map(sm, AllServicesViewModel.class)).collect(Collectors.toList());
		modelAndView.addObject("services", allServicesViewModels);

		return view(Constants.ALL_SERVICE_PAGE, modelAndView);
	}

	@GetMapping(Constants.EDIT_SERVICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.EDIT_SERVICE_TITLE)
	public ModelAndView editService(ModelAndView modelAndView, @PathVariable String id) {
		ServiceBindingModel serviceBindingModel = this.modelMapper.map(this.dollHouseService.findByID(id), ServiceBindingModel.class);
		modelAndView.addObject("officeNames", getOfficeNames());
		modelAndView.addObject("bindingModel", serviceBindingModel);

		return view(Constants.EDIT_SERVICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.EDIT_SERVICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView editServiceConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel, @PathVariable String id) {

		ServiceModel serviceModel = this.modelMapper.map(serviceBindingModel, ServiceModel.class);
		serviceModel.setId(id);
		serviceModel = this.dollHouseService.edit(serviceModel);
		if (serviceModel == null) {

			return view(Constants.EDIT_SERVICE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_SERVICE_PAGE);

	}

	@GetMapping(Constants.DELETE_SERVICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PageTitle(Constants.DELETE_SERVICE_TITLE)
	public ModelAndView deleteService(ModelAndView modelAndView, @PathVariable String id) {

		ServiceBindingModel serviceBindingModel = this.modelMapper.map(this.dollHouseService.findByID(id), ServiceBindingModel.class);
		modelAndView.addObject("officeNames", getOfficeNames());
		modelAndView.addObject("bindingModel", serviceBindingModel);

		return view(Constants.DELETE_SERVICE_PAGE, modelAndView);
	}

	@PostMapping(Constants.DELETE_SERVICE_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteServiceConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ServiceBindingModel serviceBindingModel, @PathVariable String id) {

		ServiceModel serviceModel = this.modelMapper.map(serviceBindingModel, ServiceModel.class);
		serviceModel.setId(id);
		serviceModel = this.dollHouseService.delete(serviceModel);
		if (serviceModel == null) {

			return view(Constants.DELETE_SERVICE_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_SERVICE_PAGE);
	}

	@GetMapping(Constants.FETCH_OFFICE_ALL_SERVICES_ACTION + "{officeId}")
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public List<NamesViewModel> fetchServicesByOffice(@PathVariable String officeId) {
		List<NamesViewModel> result;

		result = this.dollHouseService.findServicesByOffice(officeId).stream().map(product -> this.modelMapper.map(product, NamesViewModel.class)).collect(Collectors.toList());

		return result;
	}

	@GetMapping(Constants.FETCH_AVAILABILITIES)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public List<AvailabilityViewModel> fetchAvailabilities(@RequestParam String officeId, @RequestParam String serviceId, @RequestParam String fromDate,
	    @RequestParam String toDate) {
		List<AvailabilityViewModel> result;

		result = this.dollHouseService.fetchAvailabilities(officeId, serviceId, fromDate, toDate).stream().map(product -> this.modelMapper.map(product, AvailabilityViewModel.class))
		    .collect(Collectors.toList());

		return result;
	}

	private List<NamesViewModel> getOfficeNames() {
		List<NamesViewModel> result;

		result = this.officeService.findAllOffices().stream().map(o -> this.modelMapper.map(o, NamesViewModel.class)).collect(Collectors.toList());

		return result;
	}
}