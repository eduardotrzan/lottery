package com.system.lottery.model.wrappers;

import javax.validation.constraints.NotNull;

import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.lib.ws.LotteryDrawWS;
import com.system.lottery.model.lib.ws.TicketWS;

public final class WSBuilder {
	
	private WSBuilder() { }
	
	public final static LotteryDraw toBean(@NotNull LotteryDrawWS lotteryDrawWS) {
		LotteryDraw lotteryDraw = new LotteryDraw();
		lotteryDraw.setCombination(lotteryDrawWS.getCombination());
		lotteryDraw.setDrawOn(lotteryDrawWS.getDrawOn());
		lotteryDraw.setNumber(lotteryDrawWS.getNumber());
		lotteryDraw.setPrize(lotteryDrawWS.getPrize());
		return lotteryDraw;
	}
	
	public final static LotteryDrawWS toWS(@NotNull LotteryDraw lotteryDraw) {
		LotteryDrawWS lotteryDrawWS = new LotteryDrawWS();
		lotteryDrawWS.setCombination(lotteryDraw.getCombination());
		lotteryDrawWS.setDrawOn(lotteryDraw.getDrawOn());
		lotteryDrawWS.setNumber(lotteryDraw.getNumber());
		lotteryDrawWS.setPrize(lotteryDraw.getPrize());
		return lotteryDrawWS;
	}
	
	public final static Ticket toBean(@NotNull TicketWS ticketWS) {
		Ticket ticket = new Ticket();
		ticket.setCombination(ticketWS.getCombination());
		ticket.setNumber(ticketWS.getNumber());
		return ticket;
	}
	
	public final static TicketWS toWS(@NotNull Ticket ticket) {
		TicketWS ticketWS = new TicketWS();
		ticketWS.setCombination(ticket.getCombination());
		ticketWS.setNumber(ticket.getNumber());
		return ticketWS;
	}
	
}
