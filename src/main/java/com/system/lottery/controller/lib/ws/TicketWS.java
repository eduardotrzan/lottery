package com.system.lottery.controller.lib.ws;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketWS {

	@NotNull
	private Integer number;
	
	@NotNull
	private String name;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date drawOn;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDrawOn() {
		return drawOn;
	}

	public void setDrawOn(Date drawOn) {
		this.drawOn = drawOn;
	}
	
	@Override
	public String toString() {
		return String.format("LotteryDraw:"
				+ " [number: %s]"
				+ " [name: %s]"
				+ " [drawOn: %s]"
				, this.number
				, this.name
				, this.drawOn);
	}
}
