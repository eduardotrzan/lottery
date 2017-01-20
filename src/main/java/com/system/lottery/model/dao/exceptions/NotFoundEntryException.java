package com.system.lottery.model.dao.exceptions;

import java.util.Date;

import com.system.lottery.model.utils.AppDateUtils;

public class NotFoundEntryException extends Exception {

	private static final long serialVersionUID = 8855991981482736955L;
	
	private final static String MESSAGE = "No entry was found%s";

	public NotFoundEntryException() {
		super(String.format(MESSAGE));
	}
	
	public NotFoundEntryException(Date date) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date))));
	}
}
