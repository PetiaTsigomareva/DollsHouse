package com.petia.dollhouse.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petia.dollhouse.domain.enums.AvailabilityStatus;
import com.petia.dollhouse.utils.Utils;

public class DayAvailabilityServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	private LocalDate date;

	@NotNull()
	@NotEmpty()
	private List<HourAvailabilityServiceModel> hours;

	private DayAvailabilityServiceModel(LocalDate date) {
		this.date = date;
		hours = new ArrayList<HourAvailabilityServiceModel>();
		for (int i = 9; i <= 18; i++) {
			if (LocalDate.now().equals(date) && i <= LocalDateTime.now().getHour()) {
				hours.add(new HourAvailabilityServiceModel(Utils.format24Hour(i), AvailabilityStatus.UNAVAILABLE));
			} else {
				hours.add(new HourAvailabilityServiceModel(Utils.format24Hour(i), AvailabilityStatus.AVAILABLE));
			}
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<HourAvailabilityServiceModel> getHours() {
		return hours;
	}

	public void setHours(List<HourAvailabilityServiceModel> hours) {
		this.hours = hours;
	}

	public static List<DayAvailabilityServiceModel> constructAvailability(LocalDate fromDate, LocalDate toDate) {
		List<DayAvailabilityServiceModel> result = new ArrayList<>();
		LocalDate currentDate = fromDate;

		do {
			result.add(new DayAvailabilityServiceModel(currentDate));
			currentDate = currentDate.plusDays(1);
		} while (currentDate.isEqual(toDate) || currentDate.isBefore(toDate));

		return result;
	}

	public static final DayAvailabilityServiceModel getAvailabilityByDate(LocalDate date, List<DayAvailabilityServiceModel> availabilities) {
		DayAvailabilityServiceModel result = null;

		for (int i = 0; i < availabilities.size() && result == null; i++) {
			if (date.equals(availabilities.get(i).getDate())) {
				result = availabilities.get(i);
			}
		}

		return result;
	}

	public final HourAvailabilityServiceModel getHour(String hour) {
		HourAvailabilityServiceModel result = null;

		for (int i = 0; i < hours.size() && result == null; i++) {
			HourAvailabilityServiceModel current = hours.get(i);

			if (hour.equals(current.getHour())) {
				result = current;
			}
		}

		return result;
	}
}