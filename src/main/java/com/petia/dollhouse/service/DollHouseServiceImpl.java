package com.petia.dollhouse.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.entities.Reservation;
import com.petia.dollhouse.domain.enums.AvailabilityStatus;
import com.petia.dollhouse.domain.enums.ReservationStatus;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.DayAvailabilityServiceModel;
import com.petia.dollhouse.domain.service.HourAvailabilityServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.repositories.ReservationRepository;
import com.petia.dollhouse.repositories.ServiceRepository;
import com.petia.dollhouse.utils.Utils;

@Service
public class DollHouseServiceImpl implements DollHouseService {
	private final ServiceRepository serviceRepository;
	private final ReservationRepository reservationRepository;
	private final OfficeService officeService;
	private final ModelMapper modelMapper;

	@Autowired
	public DollHouseServiceImpl(ServiceRepository serviceRepository, ReservationRepository reservationRepository, OfficeService officeService, ModelMapper modelMapper) {
		super();
		this.serviceRepository = serviceRepository;
		this.reservationRepository = reservationRepository;
		this.officeService = officeService;
		this.modelMapper = modelMapper;
	}

	@Override
	public String add(ServiceModel model) {
		String result;
		try {

			DHService service = this.modelMapper.map(model, DHService.class);
			service.setOffice(this.findOffice(model.getOfficeId()));
			service.setStatus(StatusValues.ACTIVE);
			service = this.serviceRepository.saveAndFlush(service);
			result = service.getId();

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			result = null;
		}

		return result;
	}

	@Override
	public ServiceModel edit(ServiceModel model) {

		DHService service = this.serviceRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		model.setStatus(service.getStatus().name());

		DHService serviceNew = this.modelMapper.map(model, DHService.class);
		serviceNew.setOffice(this.findOffice(model.getOfficeId()));
		DHService mappedService = this.serviceRepository.saveAndFlush(serviceNew);

		model = this.modelMapper.map(mappedService, ServiceModel.class);

		return model;
	}

	@Override
	public List<ServiceModel> findAll() {
		List<DHService> services = this.serviceRepository.findAllActiveServices();

		List<ServiceModel> servicesModel = services.stream().map(s -> this.modelMapper.map(s, ServiceModel.class)).collect(Collectors.toList());

		return servicesModel;
	}

	@Override
	public ServiceModel findByID(String id) {
		DHService service = this.serviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		ServiceModel model = this.modelMapper.map(service, ServiceModel.class);

		return model;
	}

	@Override
	public ServiceModel delete(ServiceModel model) {
		DHService service = this.serviceRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		service.setStatus(StatusValues.INACTIVE);

		service = this.serviceRepository.saveAndFlush(service);

		model = this.modelMapper.map(service, ServiceModel.class);

		return model;
	}

	private Office findOffice(String id) {
		Office office = this.modelMapper.map(this.officeService.findOfficeByID(id), Office.class);

		return office;

	}

	@Override
	public List<ServiceModel> findServicesByOffice(String officeId) {
		List<DHService> services = this.serviceRepository.findOfficeAllActiveServices(officeId);

		List<ServiceModel> servicesModel = services.stream().map(s -> this.modelMapper.map(s, ServiceModel.class)).collect(Collectors.toList());

		return servicesModel;
	}

	@Override
	public List<DayAvailabilityServiceModel> fetchAvailabilities(String serviceId, String emloyeeId, LocalDate fromDate, LocalDate toDate) {
		List<DayAvailabilityServiceModel> result = DayAvailabilityServiceModel.constructAvailability(fromDate, toDate);

		List<Reservation> reservations = this.reservationRepository.getAllReservationsForTimePeriodOfficeServiceEmployee(serviceId, emloyeeId,
		    fromDate.format(Constants.DATE_FORMATTER), fromDate.format(Constants.DATE_FORMATTER));

		for (Reservation reservation : reservations) {
			LocalDate reservationDate = reservation.getReservationDateTime().toLocalDate();
			DayAvailabilityServiceModel dateToMark = DayAvailabilityServiceModel.getAvailabilityByDate(reservationDate, result);

			if (dateToMark != null) {
				LocalTime reservationTime = reservation.getReservationDateTime().toLocalTime();
				String reservationHour = Utils.format24Hour(reservationTime.getHour());

				HourAvailabilityServiceModel hour = dateToMark.getHour(reservationHour);
				if (hour != null) {
					ReservationStatus reservationStatus = reservation.getStatus();

					switch (reservationStatus) {
					case CONFIRMED:
						hour.setAvailability(AvailabilityStatus.UNAVAILABLE);
						break;
					case PENDING_CONFIRMATION:
						if (!AvailabilityStatus.UNAVAILABLE.equals(hour.getAvailability())) {
							hour.setAvailability(AvailabilityStatus.PENDING_CONFIRMATION);
						}
						break;
					case REJECTED:
						if (!AvailabilityStatus.UNAVAILABLE.equals(hour.getAvailability()) && !AvailabilityStatus.PENDING_CONFIRMATION.equals(hour.getAvailability())) {
							hour.setAvailability(AvailabilityStatus.AVAILABLE);
						}
						break;
					default:
						throw new RuntimeException("Invalid Reservation Status" + reservationStatus);
					}
				}
			}
		}

		return result;
	}
}