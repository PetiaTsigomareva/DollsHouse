package com.petia.dollhouse.domain.view;

public class AvailabilityViewModel extends BaseViewModel {
	private String hour;
	private boolean available;

	public AvailabilityViewModel() {
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