package com.system.lottery.controller.lib.ws;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteryDrawWS {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date drawOn;
	
	private Double prize;

	private List<Integer> combination;
	
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

	public List<Integer> getCombination() {
		return combination;
	}

	public void setCombination(List<Integer> combination) {
		this.combination = combination;
	}
	
	@Override
	public String toString() {
		return String.format("LotteryDraw:"
				+ " [drawOn: %s]"
				+ " [prize: %s]"
				+ " [combination: %s]"
				, this.drawOn
				, this.prize
				, this.combination);
	}
}
