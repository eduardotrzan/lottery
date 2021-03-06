package com.system.lottery.model.services.interfaces;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.exceptions.LotteryDrawException;

public interface LotteryService {
	
	Ticket puchaseTicket(String name) throws LotteryDrawException;
	
	LotteryDraw getLatestDrawResult() throws LotteryDrawException;
	List<LotteryDraw> getLatestYearDraws() throws LotteryDrawException;
	List<LotteryDraw> getLatestQtdDraws(int quantity) throws LotteryDrawException;
	LotteryDraw getDrawFrom(Date date) throws LotteryDrawException;
	
	Ticket checkWinner(@NotNull Ticket ticket) throws LotteryDrawException;
	Ticket checkWinner(@NotNull Ticket ticket, Date drawOn) throws LotteryDrawException;
	List<Ticket> checkWinners(@NotNull List<Ticket> tickets) throws LotteryDrawException;
	List<Ticket> checkWinners(@NotNull List<Ticket> tickets, Date drawOn) throws LotteryDrawException;
	
	Boolean isWinner(@NotNull Ticket ticket) throws LotteryDrawException;
	
	LotteryDraw drawPrize() throws LotteryDrawException;
	LotteryDraw drawPrize(Date drawForDate) throws LotteryDrawException;
	
	List<Ticket> nextDrawTickets() throws LotteryDrawException ;
	
}
