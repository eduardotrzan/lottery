package com.system.loterry.model.lib.ws;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteryDrawWS extends TicketWS {
	
	private Date drawOn;
	
	private Double prize;
	
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
