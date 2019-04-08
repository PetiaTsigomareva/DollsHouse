package com.petia.dollhouse.domain.view;

import java.time.LocalDate;
import java.util.List;

public class DayAvailabilityViewModel extends BaseViewModel {
	private LocalDate date;
	private List<HourAvailabilityViewModel> hours;

	public DayAvailabilityViewModel() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<HourAvailabilityViewModel> getHours() {
		return hours;
	}

	public void setHours(List<HourAvailabilityViewModel> hours) {
		this.hours = hours;
	}
}