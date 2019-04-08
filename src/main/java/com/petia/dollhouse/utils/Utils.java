package com.petia.dollhouse.utils;

import java.util.List;

public class Utils {
	public static final String format24Hour(int hour) {
		String result;

		if (hour <= 9) {
			result = "0" + hour + ":00";
		} else {
			result = hour + ":00";
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
}