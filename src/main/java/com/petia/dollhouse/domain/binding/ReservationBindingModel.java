package com.petia.dollhouse.domain.binding;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationBindingModel {
	private String officeId;
	private String serviceId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
