package com.petia.dollhouse.utils;

import java.time.LocalDate;
import java.util.List;

import com.petia.dollhouse.constants.Constants;

public class Utils {

	public static final String format24Hour(int hour) {
		String result;

		if (hour <= 9) {
			result = Constants.TIME_ZERO_FORMAT + hour + Constants.TIME_HOUR_FORMAT;
		} else {
			result = hour + Constants.TIME_HOUR_FORMAT;
		}

		return result;
	}

	public static final <T> T find(T t, List<T> list) {
		T result = null;

		for (int i = 0; i < list.size() && result == null; i++) {
			T current = list.get(i);

			if (t.equals(current)) {
				result = current;
			}
		}

		return result;
	}

	public static LocalDate createRandomDate(int startYear, int endYear) {
		int day = createRandomIntBetween(Constants.FIRST, Constants.DAYS);
		int month = createRandomIntBetween(Constants.FIRST, Constants.MONTH);
		int year = createRandomIntBetween(startYear, endYear);
		return LocalDate.of(year, month, day);
	}

	public static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

}