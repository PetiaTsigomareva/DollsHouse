package com.petia.dollhouse.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.petia.dollhouse.domain.view.AllOfficesViewModel;
import com.petia.dollhouse.domain.view.AllReservationViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
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
    public ReservationController(DollHouseService dollHouseService, ServiceRepository serviceRepository, OfficeService officeService, UserService userService, ReservationService reservationService, ModelMapper modelMapper) {
        this.dollHouseService = dollHouseService;
        this.serviceRepository = serviceRepository;
        this.officeService = officeService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.ADD_RESERVATION_ACTION)
    @PreAuthorize("isAuthenticated()")
    @PageTitle(Constants.ADD_RESERVATION_TITLE)
    public ModelAndView addReservation(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
        modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
        modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
        return view(Constants.ADD_RESERVATION_ACTION, modelAndView);
    }

    @PostMapping(Constants.ADD_RESERVATION_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addReservationConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
        modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
        modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
        String id = this.reservationService.add(this.modelMapper.map(reservationBindingModel, ReservationServiceModel.class));
        if (id == null) {
            return view(Constants.ADD_RESERVATION_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_RESERVATIONS_PAGE);
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
        return view(Constants.ADD_MODERATOR_RESERVATION_PAGE, modelAndView);
    }

    @PostMapping(Constants.ADD_MODERATOR_RESERVATION_ACTION)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addModeratorReservationConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
        modelAndView.addObject("officeNames", this.officeService.mapOfficeNames());
        modelAndView.addObject("serviceNames", this.dollHouseService.mapModelServiceNamesToViewNames());
        modelAndView.addObject("employeeNames", this.userService.mapUserNamesByCriteria(Constants.EMPLOYEE));
        modelAndView.addObject("customerNames", this.userService.mapUserNamesByCriteria(Constants.CUSTOMER));

        ReservationServiceModel reservationServiceModel = this.reservationService.mapBindingToServiceModel(reservationBindingModel);

        //String id = this.reservationService.addModeratorReservation(this.modelMapper.map(reservationBindingModel, ReservationServiceModel.class));
        String id = this.reservationService.addModeratorReservation(reservationServiceModel);
        if (id == null) {
            return view(Constants.ADD_MODERATOR_RESERVATION_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_RESERVATIONS_PAGE);
    }


    @GetMapping(Constants.ALL_RESERVATIONS_ACTION)
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle(Constants.ALL_RESERVATIONS_TITLE)
    public ModelAndView allReservations(ModelAndView modelAndView) {
        List<ReservationServiceModel> reservationServiceModels = this.reservationService.findAll();

        modelAndView.addObject("reservations", this.reservationService.mapServiceToViewModels(reservationServiceModels));

        return view(Constants.ALL_RESERVATIONS_PAGE, modelAndView);
    }


}
