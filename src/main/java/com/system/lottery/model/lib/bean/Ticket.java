package com.system.lottery.model.lib.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

	private Integer number;
	
	private String name;
	
	private Date drawOn;
	
	private Double prize;

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

	public Double getPrize() {
		return prize;
	}

	public void setPrize(Double prize) {
		this.prize = prize;
	}
	
	@Override
	public String toString() {
		return String.format("LotteryDraw:"
				+ " [number: %s]"
				+ " [name: %s]"
				+ " [drawOn: %s]"
				+ " [prize: %s]"
				, this.number
				, this.name
				, this.drawOn
				, this.prize);
	}
}
