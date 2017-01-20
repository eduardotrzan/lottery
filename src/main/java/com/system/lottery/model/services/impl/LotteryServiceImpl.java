package com.system.lottery.model.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.lottery.model.configuration.AppConfiguration;
import com.system.lottery.model.dao.exceptions.DuplicatedEntryException;
import com.system.lottery.model.dao.interfaces.LotteryDAO;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.exceptions.LotteryDrawException;
import com.system.lottery.model.services.interfaces.LotteryService;
import com.system.lottery.model.utils.AppDateUtils;

@Service
public class LotteryServiceImpl implements LotteryService {
	
	@Autowired
	private AppConfiguration appConfiguration;
	
	@Autowired
	private LotteryDAO lotteryDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);

	@Override
	public LotteryDraw getLatestDrawResult() {
		return this.lotteryDAO.findLast();
	}

	public List<Ticket> checkWinners(@NotNull List<Ticket> tickets) throws LotteryDrawException {
		LinkedHashMap<Long, Ticket> winnerTickets = new LinkedHashMap<>();
		Double totalPrize = this.lotteryDAO.findLast().getPrize();
		List<Double> prizeDistribution = this.appConfiguration.getPrizeDistribution();
		for (Ticket ticket : tickets) {
			int size = winnerTickets.size();
			if ((size < 3) && isWinner(ticket)) {
				ticket.setPrize(totalPrize * prizeDistribution.get(size));
				winnerTickets.put(ticket.getNumber(), ticket);
			} else {
				break;
			}
		}
		
		return winnerTickets
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	@Override
	public Boolean isWinner(@NotNull Ticket ticket) throws LotteryDrawException {
		LotteryDraw lastLotteryDraw = this.lotteryDAO.findLast();
		Date lotteryDrawOn = lastLotteryDraw.getDrawOn();
		boolean sameDrawDate = this.sameDrawDate(ticket.getDrawOn(), lotteryDrawOn);
		if (sameDrawDate) {
			return lastLotteryDraw
					.getCombination()
					.stream()
					.anyMatch(lotteryNumber -> lotteryNumber.equals(ticket.getNumber()));
		}
		throw new LotteryDrawException(ticket.getDrawOn(), "The draw from the ticket wasn't made yet.");
	}
	
	@Override
	public List<Ticket> nextDrawTickets() {
		List<Ticket> nextDrawTickets = new ArrayList<>();
		DateTime lastDrawOn = new DateTime(this.lotteryDAO.findLast().getDrawOn().getTime());
		Date nextDrawOn = lastDrawOn.plusMonths(1).toDate();
		for (long i = 1; i <= this.appConfiguration.getTotalNumbersLottery(); i++) {
			Ticket ticket = new Ticket();
			ticket.setDrawOn(nextDrawOn);
			ticket.setNumber(i);
		}
		return nextDrawTickets;
	}

	@Override
	public LotteryDraw drawPrize() throws LotteryDrawException {
		LOGGER.info("Drawing a new result...");
		Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		Date lotteryDrawOn = this.lotteryDAO.findLast().getDrawOn();
		if (this.sameDrawDate(date, lotteryDrawOn)) {
			throw new LotteryDrawException(date, "There was already a draw this month.");
		}
		
		LotteryDraw lotteryDraw = this.generateDraw(date);
		try {
			boolean isSaved = this.lotteryDAO.save(date, lotteryDraw);
			if (isSaved) {
				LOGGER.info("Result drawed for date {}.", AppDateUtils.formattedDate(date));
				return lotteryDraw;
			} else {
				throw new LotteryDrawException(date);
			}
		} catch (DuplicatedEntryException e) {
			LOGGER.error("It wasn't possible to draw a result for date {}.", date, e);
			throw new LotteryDrawException(date, e);
		}
	}
	
	private boolean sameDrawDate(Date date, Date lotteryDrawOn) {
		DateTime start = new DateTime(lotteryDrawOn.getTime());
		DateTime end = new DateTime(date.getTime());
		Months.monthsBetween(start, end);
		int months = Months.monthsBetween(start, end).getMonths();
	    return months == 1;
	}
	
	private LotteryDraw generateDraw(Date date) {
		LotteryDraw lotteryDraw = new LotteryDraw();
		lotteryDraw.setCombination(this.generateRandomCombination());
		lotteryDraw.setDrawOn(date);
		lotteryDraw.setPrize(200.0); // move to parameter
		LOGGER.info(lotteryDraw.toString());
		return lotteryDraw;
	}
	
	List<Integer> generateRandomCombination() {
		Random random = new Random();
		return random
				.ints(1, this.appConfiguration.getTotalNumbersLottery())
				.distinct()
				.limit(this.appConfiguration.getTotalNumberPerDraw())
				.boxed()
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/*
	 * General Getters and Setters
	 */

	public AppConfiguration getAppConfiguration() {
		return appConfiguration;
	}

	public void setAppConfiguration(AppConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}

	public LotteryDAO getLotteryDAO() {
		return lotteryDAO;
	}

	public void setLotteryDAO(LotteryDAO lotteryDAO) {
		this.lotteryDAO = lotteryDAO;
	}
	
}
