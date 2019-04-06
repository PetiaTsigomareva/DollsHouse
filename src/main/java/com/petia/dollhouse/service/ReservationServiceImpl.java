package com.petia.dollhouse.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.Reservation;
import com.petia.dollhouse.domain.entities.User;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	private final ModelMapper modelMapper;
	private final ReservationRepository reservationRepository;
	private final DollHouseService dollHouseService;
	private final OfficeService officeService;
	private final UserService userService;

	@Autowired
	public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, DollHouseService dollHouseService, OfficeService officeService,
	    UserService userService) {
		this.modelMapper = modelMapper;
		this.reservationRepository = reservationRepository;
		this.dollHouseService = dollHouseService;
		this.officeService = officeService;
		this.userService = userService;
	}

	@Override
	public String add(ReservationServiceModel model) {
		String result;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Reservation reservation = this.modelMapper.map(model, Reservation.class);
		reservation.setOffice(this.findOffice(model.getOfficeId()));
		reservation.setStatus(StatusValues.ACTIVE);
		reservation.setServices(this.findServices(model.getServiceIds()));
		reservation.setCustomer(this.modelMapper.map(userService.findUserByUserName(currentPrincipalName), User.class));
		reservation = this.reservationRepository.saveAndFlush(reservation);
		result = reservation.getId();

		return result;
	}

	@Override
	public ReservationServiceModel edit(ReservationServiceModel model) {
		return null;
	}

	@Override
	public List<ReservationServiceModel> findAll() {
		return null;
	}

	@Override
	public ReservationServiceModel findByID(String id) {
		return null;
	}

	@Override
	public ReservationServiceModel delete(ReservationServiceModel model) {
		return null;
	}

	private Office findOffice(String id) {
		Office office = this.modelMapper.map(this.officeService.findOfficeByID(id), Office.class);

		return office;

	}

	private List<DHService> findServices(List<String> ids) {
		List<DHService> result = new ArrayList<>();

		for (String id : ids) {
			DHService service = this.modelMapper.map(this.dollHouseService.findByID(id), DHService.class);
			result.add(service);

		}

		return result;

	}
}
