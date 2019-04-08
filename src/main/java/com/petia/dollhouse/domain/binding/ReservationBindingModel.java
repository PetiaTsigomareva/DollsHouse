package com.petia.dollhouse.domain.binding;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

public class ReservationBindingModel {
	private String employeeId;
	private String serviceId;
	@DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
	private LocalDateTime reservationDateTime;
	private String description;

	public ReservationBindingModel() {
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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
