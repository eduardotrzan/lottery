package com.system.lottery.model.lib.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

	private Long number;
	
	private String name;
	
	private Date drawOn;
	
	private Double prize;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
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

	public Double getPrize() {
		return prize;
	}

	public void setPrize(Double prize) {
		this.prize = prize;
	}
}
