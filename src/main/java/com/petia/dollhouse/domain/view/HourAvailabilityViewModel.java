package com.petia.dollhouse.domain.view;

import com.petia.dollhouse.domain.enums.AvailabilityStatus;

public class HourAvailabilityViewModel extends BaseViewModel {
	private String hour;
	private AvailabilityStatus availability;

	public HourAvailabilityViewModel() {
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