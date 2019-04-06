package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.service.DollHouseService;
import com.petia.dollhouse.service.OfficeService;
import com.petia.dollhouse.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController extends BaseController {
    private final DollHouseService dollHouseService;
    private final OfficeService officeService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(DollHouseService dollHouseService, OfficeService officeService, ReservationService reservationService, ModelMapper modelMapper) {
        this.dollHouseService = dollHouseService;
        this.officeService = officeService;
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;

    @GetMapping(Constants.ADD_RESERVATION_ACTION)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addReservation(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
        modelAndView.addObject("officeNames", getOfficeNames());
        modelAndView.addObject("serviceNames", getServiceNames());
        return view(Constants.ADD_RESERVATION_ACTION, modelAndView);
    }

    @PostMapping(Constants.ADD_RESERVATION_ACTION)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addReservationConfirm(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") ReservationBindingModel reservationBindingModel) {
        modelAndView.addObject("officeNames", getOfficeNames());
        modelAndView.addObject("serviceNames", getServiceNames());
        String id = this.reservationService.add(this.modelMapper.map(reservationBindingModel, ReservationServiceModel.class));
        if (id == null) {
            return view(Constants.ADD_RESERVATION_PAGE, modelAndView);
        }

        return redirect(Constants.ALL_RESERVATIONS_PAGE);
    }

    private List<NamesViewModel> getOfficeNames() {
        List<NamesViewModel> result;
        result = this.officeService.findAllOffices().stream().map(o -> this.modelMapper.map(o, NamesViewModel.class)).collect(Collectors.toList());

        return result;

    }

    private List<NamesViewModel> getServiceNames() {
        List<NamesViewModel> result;
        result = this.dollHouseService.findAll().stream().map(s -> this.modelMapper.map(s, NamesViewModel.class)).collect(Collectors.toList());

        return result;

    }

}
