package com.petia.dollhouse.web.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.repositories.ServiceRepository;
import com.petia.dollhouse.service.DollHouseService;
import com.petia.dollhouse.service.OfficeService;
import com.petia.dollhouse.service.ReservationService;
import com.petia.dollhouse.service.UserService;
import com.petia.dollhouse.web.annotations.PageTitle;

@Controller
public class ReservationController extends BaseController {
	private final DollHouseService dollHouseService;
	private final ServiceRepository serviceRepository;
	private final OfficeService officeService;
	private final UserService userService;
	private final ReservationService reservationService;
	private final ModelMapper modelMapper;

	@Autowired
	public ReservationController(DollHouseService dollHouseService, ServiceRepository serviceRepository, OfficeService officeService, UserService userService,
	    ReservationService reservationService, ModelMapper modelMapper) {
		this.dollHouseService = dollHouseService;
		this.serviceRepository = serviceRepository;
		this.officeService = officeService;
		this.userService = userService;
		this.reservationService = reservationService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(Constants.MY_RESERVATIONS_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.MY_RESERVATIONS_TITLE)
	public ModelAndView myReservations(ModelAndView modelAndView, Principal principal) {
		List<ReservationServiceModel> reservationServiceModels = this.reservationService.findALLByUser(principal.getName());

		modelAndView.addObject("reservations", this.reservationService.mapServiceToViewModels(reservationServiceModels));

		return view(Constants.MY_RESERVATIONS_PAGE, modelAndView);
	}

	@GetMapping(Constants.ADD_RESERVATION_ACTION)
	@PreAuthorize("isAuthenticated()")
	@PageTitle(Constants.ADD_RESERVATION_TITLE)
	public ModelAndView addReservation(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());

		return view(Constants.ADD_RESERVATION_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_RESERVATION_ACTION)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView addReservationConfirm(HttpServletRequest request, ModelAndView modelAndView,
	    @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());

		String selectedHour = request.getParameter("selectedHour");
		LocalDateTime selectedHourDateTime = LocalDateTime.parse(selectedHour, Constants.DATE_TIME_FORMATTER);

		reservationBindingModel.setReservationDateTime(selectedHourDateTime);

		String id = this.reservationService.add(this.reservationService.mapBindingToServiceModel(reservationBindingModel));
		if (id == null) {
			return view(Constants.ADD_RESERVATION_PAGE, modelAndView);
		}

		return redirect(Constants.MY_RESERVATIONS_PAGE);
	}

	@GetMapping(Constants.ALL_RESERVATIONS_ACTION)
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	@PageTitle(Constants.ALL_RESERVATIONS_TITLE)
	public ModelAndView allReservations(ModelAndView modelAndView) {
		List<ReservationServiceModel> reservationServiceModels = this.reservationService.findAll();

		modelAndView.addObject("reservations", this.reservationService.mapServiceToViewModels(reservationServiceModels));

		return view(Constants.ALL_RESERVATIONS_PAGE, modelAndView);
	}

	// Moderator reservation form
	@GetMapping(Constants.ADD_MODERATOR_RESERVATION_ACTION)
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	@PageTitle(Constants.ADD_RESERVATION_TITLE)
	public ModelAndView addModeratorReservation(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
		modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
		modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
		modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());
		return view(Constants.ADD_MODERATOR_RESERVATION_PAGE, modelAndView);
	}

	@PostMapping(Constants.ADD_MODERATOR_RESERVATION_ACTION)
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	public ModelAndView addModeratorReservationConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel,
	    BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return super.view(Constants.ADD_MODERATOR_RESERVATION_PAGE);
		}

		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
		modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
		modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
		modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());

		ReservationServiceModel reservationServiceModel = this.reservationService.mapBindingToServiceModel(reservationBindingModel);

		String id = this.reservationService.addModeratorReservation(reservationServiceModel);
		if (id == null) {
			return view(Constants.ADD_MODERATOR_RESERVATION_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_RESERVATIONS_PAGE);
	}

	@GetMapping(Constants.EDIT_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	@PageTitle(Constants.EDIT_RESERVATION_TITLE)
	public ModelAndView editModeratorReservation(ModelAndView modelAndView, @PathVariable String id) {
		ReservationBindingModel reservationBindingModel = this.modelMapper.map(this.reservationService.findByID(id), ReservationBindingModel.class);
		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
		modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
		modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
		modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());
		modelAndView.addObject("bindingModel", reservationBindingModel);

		return view(Constants.EDIT_MODERATOR_RESERVATION_PAGE, modelAndView);
	}

	@PostMapping(Constants.EDIT_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	public ModelAndView editModeratorReservationConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel,
	    @PathVariable String id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return super.view(Constants.EDIT_MODERATOR_RESERVATION_PAGE);
		}

		ReservationServiceModel reservationServiceModel = this.reservationService.mapBindingToServiceModel(reservationBindingModel);
		reservationServiceModel.setId(id);

		reservationServiceModel = this.reservationService.edit(reservationServiceModel);
		if (reservationServiceModel == null) {
			modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
			modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
			modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
			modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
			modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());
			modelAndView.addObject("bindingModel", reservationBindingModel);

			return view(Constants.EDIT_MODERATOR_RESERVATION_PAGE, modelAndView);
		}

		return redirect(Constants.ALL_RESERVATIONS_PAGE);

	}

	@GetMapping(Constants.REJECT_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	@PageTitle(Constants.REJECT_RESERVATION_TITLE)
	public ModelAndView rejectReservation(ModelAndView modelAndView, @PathVariable String id) {
		ReservationBindingModel reservationBindingModel = this.modelMapper.map(this.reservationService.findByID(id), ReservationBindingModel.class);

		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
		modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
		modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
		modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());
		modelAndView.addObject("bindingModel", reservationBindingModel);

		return view(Constants.REJECT_MODERATOR_RESERVATION_PAGE, modelAndView);
	}

	@PostMapping(Constants.REJECT_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	public ModelAndView rejectReservationConfirm(@PathVariable String id) {

		this.reservationService.setReservationStatus(id, Constants.RESERVATION_REJECT);

		return redirect(Constants.ALL_RESERVATIONS_PAGE);
	}

	@GetMapping(Constants.CONFIRM_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	@PageTitle(Constants.CONFIRM_RESERVATION_TITLE)
	public ModelAndView confirmReservation(ModelAndView modelAndView, @PathVariable String id) {
		ReservationBindingModel reservationBindingModel = this.modelMapper.map(this.reservationService.findByID(id), ReservationBindingModel.class);

		modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
		modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
		modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
		modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));
		modelAndView.addObject("statusReservations", this.reservationService.getReservationStatusValues());
		modelAndView.addObject("bindingModel", reservationBindingModel);

		return view(Constants.CONFIRM_MODERATOR_RESERVATION_PAGE, modelAndView);
	}

	@PostMapping(Constants.CONFIRM_MODERATOR_RESERVATION_ACTION + "{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	public ModelAndView confirmReservation(@PathVariable String id) {

		this.reservationService.setReservationStatus(id, Constants.RESERVATION_CONFIRM);

		return redirect(Constants.ALL_RESERVATIONS_PAGE);
	}
}