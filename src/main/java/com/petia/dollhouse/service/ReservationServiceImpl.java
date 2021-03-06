package com.petia.dollhouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.Reservation;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.ReservationStatus;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.service.UserServiceModel;
import com.petia.dollhouse.domain.view.AllReservationViewModel;
import com.petia.dollhouse.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	private final ModelMapper modelMapper;
	private final ReservationRepository reservationRepository;
	private final DollHouseService dollHouseService;
	private final UserService userService;
	private final OfficeService officeService;

	@Autowired
	public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, DollHouseService dollHouseService, UserService userService,
			OfficeService officeService) {
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
		reservation.setStatus(ReservationStatus.PendingConfirmation);
		// TODO ERROR Handling
//        if (this.reservationRepository.findByReservationDateTime(LocalDateTime time).orElse(null) != null) {
//
//             throw new IllegalArgumentException(Constants.EXIST_ITEM_ERROR_MESSAGE);
//
//        }
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
		reservation.setStatus(ReservationStatus.valueOf(model.getStatus()));
		// TODO ERROR Handling
		if (this.reservationRepository.findByReservationDateTime(reservation.getReservationDateTime()).orElse(null) != null) {

			throw new IllegalArgumentException(Constants.EXIST_ITEM_ERROR_MESSAGE);

		}
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
		Reservation reservation = this.reservationRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		reservation = this.modelMapper.map(model, Reservation.class);
		reservation.setOffice(this.modelMapper.map(this.officeService.findOfficeByID(model.getOfficeId()), Office.class));
		reservation.setService(this.modelMapper.map(this.dollHouseService.findByID(model.getServiceId()), DHService.class));
		reservation.setEmployee(this.modelMapper.map(this.userService.findUserById(model.getEmployeeId()), User.class));
		reservation.setCustomer(this.modelMapper.map(this.userService.findUserById(model.getCustomerId()), User.class));
		reservation.setStatus(ReservationStatus.valueOf(model.getStatus()));
		reservation = this.reservationRepository.saveAndFlush(reservation);

		model = this.modelMapper.map(reservation, ReservationServiceModel.class);

		return model;
	}

	@Override
	public void setReservationStatus(String id, String status) {
		Reservation reservation = this.reservationRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		switch (status) {
		case Constants.RESERVATION_CONFIRM:
			reservation.setStatus(ReservationStatus.Confirmed);
			break;
		case Constants.RESERVATION_PENDING:
			reservation.setStatus(ReservationStatus.PendingConfirmation);
			break;
		case Constants.RESERVATION_REJECT:
			reservation.setStatus(ReservationStatus.Rejected);
			break;
		}

		this.reservationRepository.saveAndFlush(reservation);
	}

	@Override
	public List<AllReservationViewModel> mapServiceToViewModels(List<ReservationServiceModel> models) {
		List<AllReservationViewModel> result = new ArrayList<>();
		for (ReservationServiceModel m : models) {
			AllReservationViewModel allReservationViewModel = new AllReservationViewModel();
			allReservationViewModel.setId(m.getId());
			allReservationViewModel.setOffice(this.getOfficeName(m.getOfficeId()));
			allReservationViewModel.setService(this.getServiceName(m.getServiceId()));
			allReservationViewModel.setEmployee(this.getUserName(m.getEmployeeId()));
			allReservationViewModel.setCustomer(this.getUserName(m.getCustomerId()));
			allReservationViewModel.setReservationDateTime(m.getReservationDateTime());
			allReservationViewModel.setReservationStatus(m.getStatus());

			result.add(allReservationViewModel);
		}
		return result;
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
		result.setStatus(model.getReservationStatus());
		return result;
	}

	@Override
	public List<String> getReservationStatusValues() {
		List<String> result = new ArrayList<>();
		for (ReservationStatus rs : ReservationStatus.values()) {
			result.add(rs.name());
		}
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

	@Override
	public List<ReservationServiceModel> findALLByUser(String username) {
		UserServiceModel user = this.userService.findUserByUserName(username);

		List<Reservation> reservations = this.reservationRepository.findAllByUsername(user.getId());

		List<ReservationServiceModel> result = reservations.stream().map(r -> this.modelMapper.map(r, ReservationServiceModel.class)).collect(Collectors.toList());

		return result;
	}

}
