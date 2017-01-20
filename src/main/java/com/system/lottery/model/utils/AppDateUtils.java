package com.system.lottery.model.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class AppDateUtils {
	
	private AppDateUtils() { }
	
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
	
	public static String formattedDate(Date date) {
		return DATE_FORMAT.format(date);
	}

}
