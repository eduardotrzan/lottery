package com.system.lottery.model.lib.ws;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketWS {

	private Integer number;
	
	private List<Integer> combination;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Integer> getCombination() {
		return combination;
	}

	public void setCombination(List<Integer> combination) {
		this.combination = combination;
	}
}
