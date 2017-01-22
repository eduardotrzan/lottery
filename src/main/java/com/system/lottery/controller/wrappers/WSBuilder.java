package com.system.lottery.controller.wrappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.system.lottery.controller.lib.ws.LotteryDrawWS;
import com.system.lottery.controller.lib.ws.LotteryResultWS;
import com.system.lottery.controller.lib.ws.TicketWS;
import com.system.lottery.controller.lib.ws.WinnerWS;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;

public final class WSBuilder {
	
	private WSBuilder() { }
	
	public final static LotteryDraw toLotteryDrawBean(@NotNull LotteryDrawWS lotteryDrawWS) {
		LotteryDraw lotteryDraw = new LotteryDraw();
		lotteryDraw.setCombination(lotteryDrawWS.getCombination());
		lotteryDraw.setDrawOn(lotteryDrawWS.getDrawOn());
		lotteryDraw.setPrize(lotteryDrawWS.getPrize());
		return lotteryDraw;
	}
	
	public final static List<LotteryDraw> toLotteryDrawBean(List<LotteryDrawWS> lotteryDrawWSs) {
		List<LotteryDraw> lotteryDraws = new ArrayList<>();
		for (LotteryDrawWS lotteryDrawWS : lotteryDrawWSs) {
			lotteryDraws.add(toLotteryDrawBean(lotteryDrawWS));
		}
		return lotteryDraws;
	}
	
	public final static LotteryDrawWS toLotteryDrawWS(@NotNull LotteryDraw lotteryDraw) {
		LotteryDrawWS lotteryDrawWS = new LotteryDrawWS();
		lotteryDrawWS.setCombination(lotteryDraw.getCombination());
		lotteryDrawWS.setDrawOn(lotteryDraw.getDrawOn());
		lotteryDrawWS.setPrize(lotteryDraw.getPrize());
		return lotteryDrawWS;
	}
	
	public final static List<LotteryDrawWS> toLotteryDrawWS(List<LotteryDraw> lotteryDraws) {
		List<LotteryDrawWS> lotteryDrawWSs = new ArrayList<>();
		for (LotteryDraw lotteryDraw : lotteryDraws) {
			lotteryDrawWSs.add(toLotteryDrawWS(lotteryDraw));
		}
		return lotteryDrawWSs;
	}

	public final static List<Ticket> toBean(@NotNull List<TicketWS> ticketWSs) {
		List<Ticket> tickets = new ArrayList<>();
		for (TicketWS ticketWS : ticketWSs) {
			tickets.add(toBean(ticketWS));
		}
		return tickets;
	}
	
	public final static Ticket toBean(@NotNull TicketWS ticketWS) {
		Ticket ticket = new Ticket();
		ticket.setName(ticketWS.getName());
		ticket.setNumber(ticketWS.getNumber());
		ticket.setDrawOn(ticketWS.getDrawOn());
		return ticket;
	}
	
	public final static List<TicketWS> toWS(List<Ticket> tickets) {
		List<TicketWS> ticketWSs = new ArrayList<>();
		for (Ticket ticket : tickets) {
			ticketWSs.add(toWS(ticket));
		}
		return ticketWSs;
	}
	
	public final static TicketWS toWS(@NotNull Ticket ticket) {
		TicketWS ticketWS = new TicketWS();
		ticketWS.setName(ticket.getName());
		ticketWS.setNumber(ticket.getNumber());
		ticketWS.setDrawOn(ticket.getDrawOn());
		return ticketWS;
	}
	
	public static LotteryResultWS build(@NotNull List<Ticket> tickets) {
		return build(null, tickets);
	}
	
	public static LotteryResultWS build(Date drawOn, @NotNull List<Ticket> tickets) {
		LotteryResultWS lotteryResultWS = new LotteryResultWS();
		lotteryResultWS.setDrawOn(drawOn);
		
		for (int i = 0; i < tickets.size(); i++) {
			Ticket ticket = tickets.get(i);
			
			WinnerWS winnerWS = new WinnerWS();
			winnerWS.setName(ticket.getName());
			winnerWS.setPosition(i);
			winnerWS.setValue(ticket.getPrize());
			
			lotteryResultWS.getWinners().add(winnerWS);
		}
		
		return lotteryResultWS;
	}
}
