package com.petia.dollhouse.domain.binding;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

public class ReservationBindingModel {
	private String officeId;
	private String serviceId;
	private String employeeId;
	private String customerId;
	@DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
	private LocalDateTime reservationDateTime;
	private String description;

	public ReservationBindingModel() {
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
