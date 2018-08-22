package com.procedure.scheduling.common.utils;

import com.procedure.scheduling.common.exceptions.NonInstanceException;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

	private DateUtils() {

		throw new NonInstanceException(DateUtils.class);
	}

	public static Date from(int year, int month, int day, int hour, int minute) {

		Calendar instance = Calendar.getInstance();
		instance.set(year, month, day, hour, minute, 0);
		instance.set(Calendar.MILLISECOND, 0);
		return instance.getTime();
	}

	public static Date from(int year, int month, int day) {

		return from(year, month, day, 0, 0);
	}

	public static Date roundQuarterHour(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int nonRoundedMinutes = calendar.get(Calendar.MINUTE);
		int mod = nonRoundedMinutes % 15;
		calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
		return calendar.getTime();
	}

	public static Date now() {

		return new Date();
	}

	public static Date toEndOfDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date toStartOfDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
