package com.system.loterry.model.lib.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteryResultWS {
	
	public LotteryDrawWS lotteryDraw;
	
	public Boolean winner;

	public LotteryDrawWS getLotteryDraw() {
		return lotteryDraw;
	}

	public void setLotteryDraw(LotteryDrawWS lotteryDraw) {
		this.lotteryDraw = lotteryDraw;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
}
