package com.system.lottery.model.services.exceptions;

import java.util.Date;

import com.system.lottery.model.utils.AppDateUtils;

public class LotteryDrawException extends Exception {
	
	private static final long serialVersionUID = 2945218305073442134L;
	
	private final static String MESSAGE = "An issue happened during the lottery draw%s.";

	public LotteryDrawException() {
		super(String.format(MESSAGE));
	}
	
	public LotteryDrawException(Date date) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date))));
	}
	
	public LotteryDrawException(Date date, Exception e) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date))), e);
	}
	
	public LotteryDrawException(Date date, String extraDetails) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date) + ".\n" + extraDetails)));
	}
	
	public LotteryDrawException(Date date, String extraDetails, Exception e) {
		super(String.format(MESSAGE, (" for date " + AppDateUtils.formattedDate(date) + ".\n" + extraDetails)), e);
	}
}
