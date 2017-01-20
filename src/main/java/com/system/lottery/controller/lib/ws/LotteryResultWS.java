package com.system.lottery.controller.lib.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteryResultWS {
	
	public List<WinnerWS> winners;
	
	public Date drawOn;
	
	public LotteryResultWS() {
		this.winners = new ArrayList<>();
	}

	public List<WinnerWS> getWinners() {
		return winners;
	}

	public void setWinners(List<WinnerWS> winners) {
		this.winners = winners;
	}

	public Date getDrawOn() {
		return drawOn;
	}

	public void setDrawOn(Date drawOn) {
		this.drawOn = drawOn;
	}
}
