package com.petia.dollhouse.domain.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petia.dollhouse.domain.enums.AvailabilityStatus;

public class HourAvailabilityServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	private String hour;

	@NotNull()
	@NotEmpty()
	private AvailabilityStatus availability;

	public HourAvailabilityServiceModel(String hour, AvailabilityStatus availability) {
		this.hour = hour;
		this.availability = availability;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public AvailabilityStatus getAvailability() {
		return availability;
	}

	public void setAvailability(AvailabilityStatus availability) {
		this.availability = availability;
	}
}