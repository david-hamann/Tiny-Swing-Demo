package com.flamingmarshmallow.demo.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	public static String prettyDate(final long timestamp) {
		if (timestamp <= 0) {
			return "";
		}
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
}
