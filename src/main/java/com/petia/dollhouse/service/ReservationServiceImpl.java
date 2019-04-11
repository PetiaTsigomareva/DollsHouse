package com.petia.dollhouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.enums.ReservationStatus;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.AllReservationViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Reservation;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ModelMapper modelMapper;
    private final ReservationRepository reservationRepository;
    private final DollHouseService dollHouseService;
    private final UserService userService;
    private final OfficeService officeService;

    @Autowired
    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, DollHouseService dollHouseService, UserService userService, OfficeService officeService) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.dollHouseService = dollHouseService;
        this.userService = userService;
        this.officeService = officeService;
    }

    @Override
    public String add(ReservationServiceModel model) {
        String result;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Reservation reservation = this.modelMapper.map(model, Reservation.class);
        reservation.setEmployee(this.modelMapper.map(this.userService.findUserById(model.getEmployeeId()), User.class));
        reservation.setService(this.modelMapper.map(this.dollHouseService.findByID(model.getServiceId()), DHService.class));
        reservation.setCustomer(this.modelMapper.map(userService.findUserByUserName(currentPrincipalName), User.class));
        reservation = this.reservationRepository.saveAndFlush(reservation);
        result = reservation.getId();

        return result;
    }

    @Override
    public String addModeratorReservation(ReservationServiceModel model) {
        String result;
        Reservation reservation = this.modelMapper.map(model, Reservation.class);
        reservation.setEmployee(this.modelMapper.map(this.userService.findUserById(model.getEmployeeId()), User.class));
        reservation.setService(this.modelMapper.map(this.dollHouseService.findByID(model.getServiceId()), DHService.class));
        reservation.setCustomer(this.modelMapper.map(userService.findUserById(model.getCustomerId()), User.class));
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation = this.reservationRepository.saveAndFlush(reservation);
        result = reservation.getId();

        return result;
    }


    @Override
    public List<ReservationServiceModel> findAll() {
        List<Reservation> reservations = this.reservationRepository.findAll();
        List<ReservationServiceModel> result = reservations.stream().map(r -> this.modelMapper.map(r, ReservationServiceModel.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public ReservationServiceModel findByID(String id) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
        ReservationServiceModel reservationServiceModel = this.modelMapper.map(reservation, ReservationServiceModel.class);
        return reservationServiceModel;
    }

    @Override
    public ReservationServiceModel edit(ReservationServiceModel model) {
        return null;
    }

    @Override
    public ReservationServiceModel delete(ReservationServiceModel model) {
        return null;
    }

    @Override
    public List<AllReservationViewModel> mapServiceToViewModels(List<ReservationServiceModel> models) {
        List<AllReservationViewModel> results = new ArrayList<>();
        for (ReservationServiceModel m : models) {
            AllReservationViewModel allReservationViewModel = new AllReservationViewModel();
            allReservationViewModel.setOffice(this.getOfficeName(m.getOfficeId()));
            allReservationViewModel.setService(this.getServiceName(m.getServiceId()));
            allReservationViewModel.setEmployee(this.getUserName(m.getEmployeeId()));
            allReservationViewModel.setCustomer(this.getUserName(m.getCustomerId()));
            allReservationViewModel.setReservationDateTime(m.getReservationDateTime());
        }
        return results;
    }

    @Override
    public ReservationServiceModel mapBindingToServiceModel(ReservationBindingModel model) {
        ReservationServiceModel result = new ReservationServiceModel();
        result.setOfficeId(model.getOfficeId());
        result.setServiceId(model.getServiceId());
        result.setEmployeeId(model.getEmployeeId());
        result.setCustomerId(model.getCustomerId());
        result.setReservationDateTime(model.getReservationDateTime());
        result.setDescription(model.getDescription());
        return result;
    }

    private String getOfficeName(String id) {
        String result;
        OfficeServiceModel office = this.officeService.findOfficeByID(id);
        result = office.getName();
        return result;
    }

    private String getServiceName(String id) {
        String result;
        ServiceModel serviceModel = this.dollHouseService.findByID(id);
        result = serviceModel.getName();
        return result;
    }

    private String getUserName(String id) {
        StringBuilder result = new StringBuilder();
        UserServiceModel userServiceModel = this.userService.findUserById(id);
        result.append(userServiceModel.getFirstName()).append(" ").append(userServiceModel.getLastName()).append("(").append(userServiceModel.getUsername()).append(")");
        return result.toString();
    }

}
