package com.system.lottery.model.dao.exceptions;

import java.util.Date;

import com.system.lottery.model.utils.AppDateUtils;

public class DuplicatedEntryException extends Exception {

	private static final long serialVersionUID = 8855991981482736955L;
	
	private final static String MESSAGE = "A duplicated entry was found%s.";

	public DuplicatedEntryException() {
		super(MESSAGE);
	}
	
	public DuplicatedEntryException(Date date) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date))));
	}
}
