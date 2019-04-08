package com.petia.dollhouse.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

public class ReservationServiceModel extends BaseServiceModel {
	private String employeeId;
	private List<String> serviceIds;
	@DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
	private LocalDateTime reservationDateTime;
	private String description;

	public ReservationServiceModel() {
		serviceIds = new ArrayList<>();
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<String> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public LocalDateTime getReservationDateTime() {
		return reservationDateTime;
	}

	public void setReservationDateTime(LocalDateTime reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
