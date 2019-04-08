package com.petia.dollhouse.domain.service;

public class AvailabilityServiceModel extends BaseServiceModel {
	private String hour;
	private boolean available;

	public AvailabilityServiceModel() {
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}