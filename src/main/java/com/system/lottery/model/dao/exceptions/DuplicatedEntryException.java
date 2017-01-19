package com.system.lottery.model.dao.exceptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DuplicatedEntryException extends Exception {

	private static final long serialVersionUID = 8855991981482736955L;
	
	private final static String MESSAGE = "A duplicated entry was found%s.";
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	public DuplicatedEntryException() {
		super(MESSAGE);
	}
	
	public DuplicatedEntryException(Date date) {
		super(String.format(MESSAGE, (" for date " + DATE_FORMAT.format(date))));
	}
}
