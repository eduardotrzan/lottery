package com.system.lottery.model.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public Ticket puchaseTicket(String name) throws LotteryDrawException {
		if ((name == null) || name.trim().isEmpty()) {
			throw new LotteryDrawException(new Date(), "Can't purchase a ticket without providing a name.");
		}
		
		Ticket ticket = new Ticket();
		ticket.setName(name);
		
		LotteryDraw lotteryDraw = this.getLatestDrawResult();
		Date drawDate = this.getDateForDraw(lotteryDraw.getDrawOn());
		ticket.setDrawOn(drawDate);
		
		Integer randomNumber = this.generateRandomCombination().get(0);
		ticket.setNumber(randomNumber);
		
		return ticket;
	}
	
	private Date getDateForDraw(Date dateForDraw) {
		return Date.from(
				dateForDraw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.plusMonths(1)
					.atStartOfDay(ZoneId.systemDefault())
					.toInstant()
				);
	}

	@Override
	public LotteryDraw getLatestDrawResult() throws LotteryDrawException {
		LotteryDraw lotteryDraw = this.lotteryDAO.findLast(); 
		if (lotteryDraw == null) {
			lotteryDraw = this.drawPrize();
		}
		return lotteryDraw;
	}
	
	@Override
	public List<LotteryDraw> getLatestYearDraws() throws LotteryDrawException {
		return this.getLatestQtdDraws(12);
	}
	
	@Override
	public List<LotteryDraw> getLatestQtdDraws(int quantity) throws LotteryDrawException {
		return this.lotteryDAO.findAll(quantity);
	}
	
	@Override
	public LotteryDraw getDrawFrom(Date drawForDate) throws LotteryDrawException {
		LotteryDraw lotteryDraw = this.lotteryDAO.findByDate(drawForDate); 
		if (lotteryDraw == null) {
			lotteryDraw = this.drawPrize(drawForDate);
		}
		return lotteryDraw;
	}

	@Override
	public Ticket checkWinner(@NotNull Ticket ticket) throws LotteryDrawException {
		List<Ticket> tickets = this.checkWinners(Arrays.asList(ticket));
		return tickets.isEmpty() ? null : tickets.get(0);
	}
	
	@Override
	public Ticket checkWinner(@NotNull Ticket ticket, Date drawOn) throws LotteryDrawException {
		List<Ticket> tickets = this.checkWinners(Arrays.asList(ticket), drawOn);
		return tickets.isEmpty() ? null : tickets.get(0);
	}
	
	@Override
	public List<Ticket> checkWinners(@NotNull List<Ticket> tickets) throws LotteryDrawException {
		return this.checkWinners(tickets, null);
	}
	
	@Override
	public List<Ticket> checkWinners(@NotNull List<Ticket> tickets, Date drawOn) throws LotteryDrawException {
		LinkedHashMap<Integer, Ticket> winnerTickets = new LinkedHashMap<>();
		
		LotteryDraw lotteryDraw;
		if (drawOn == null) {
			lotteryDraw = this.getLatestDrawResult();
		} else {
			lotteryDraw = this.getDrawFrom(drawOn);
		}
		
		Map<Integer, Double> prizePerPosition = this.prizePerPosition(lotteryDraw);
		
		Double totalPrize = lotteryDraw.getPrize();
		for (Ticket ticket : tickets) {
			int size = winnerTickets.size();
			if (size > 3) {
				break;
			}
			
			if (isWinner(ticket)) {
				double prizeForPosition = prizePerPosition.get(ticket.getNumber());
				ticket.setPrize(totalPrize * prizeForPosition);
				winnerTickets.put(ticket.getNumber(), ticket);
			}
		}
		
		return winnerTickets
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	private Map<Integer, Double> prizePerPosition(LotteryDraw lotteryDraw) throws LotteryDrawException {
		List<Double> prizeDistribution = this.appConfiguration.getPrizeDistribution();
		List<Integer> combination = lotteryDraw.getCombination();
		Map<Integer, Double> prizePerPosition = new HashMap<>();
		
		for (int i = 0; i < prizeDistribution.size(); i++) {
			prizePerPosition.put(combination.get(i), prizeDistribution.get(i));
		}
		return prizePerPosition;
	}
	
	@Override
	public Boolean isWinner(@NotNull Ticket ticket) throws LotteryDrawException {
		LotteryDraw lastLotteryDraw = this.getLatestDrawResult();
		Date lotteryDrawOn = lastLotteryDraw.getDrawOn();
		boolean sameDrawDate = this.sameDrawMonth(ticket.getDrawOn(), lotteryDrawOn);
		if (sameDrawDate) {
			return lastLotteryDraw
					.getCombination()
					.stream()
					.anyMatch(lotteryNumber -> lotteryNumber.intValue() == ticket.getNumber().intValue());
		}
		throw new LotteryDrawException(ticket.getDrawOn(), "The draw from the ticket wasn't made yet.");
	}
	
	@Override
	public List<Ticket> nextDrawTickets() throws LotteryDrawException {
		List<Ticket> nextDrawTickets = new ArrayList<>();
		DateTime lastDrawOn = new DateTime(this.getLatestDrawResult().getDrawOn().getTime());
		Date nextDrawOn = lastDrawOn.plusMonths(1).toDate();
		for (int i = 1; i <= this.appConfiguration.getTotalNumbersLottery(); i++) {
			Ticket ticket = new Ticket();
			ticket.setDrawOn(nextDrawOn);
			ticket.setNumber(i);
		}
		return nextDrawTickets;
	}
	
	@Override
	public LotteryDraw drawPrize() throws LotteryDrawException {
		Date drawForDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		return this.drawPrize(drawForDate);
	}

	@Override
	public LotteryDraw drawPrize(Date drawForDate) throws LotteryDrawException {
		LOGGER.info("Drawing a new result for date {}...", drawForDate);
		
		Date lotteryDrawOn = null;
		LotteryDraw lastLotteryDraw = this.lotteryDAO.findLast();
		if (lastLotteryDraw != null) {
			lotteryDrawOn = lastLotteryDraw.getDrawOn();
		}
		if (this.sameDrawMonth(drawForDate, lotteryDrawOn)) {
			throw new LotteryDrawException(drawForDate, "There was already a draw this month.");
		}
		
		LotteryDraw lotteryDraw = this.generateDraw(drawForDate);
		try {
			boolean isSaved = this.lotteryDAO.save(drawForDate, lotteryDraw);
			if (isSaved) {
				LOGGER.info("Result drawed for date {}.", AppDateUtils.formattedDate(drawForDate));
				return lotteryDraw;
			} else {
				throw new LotteryDrawException(drawForDate);
			}
		} catch (DuplicatedEntryException e) {
			LOGGER.error("It wasn't possible to draw a result for date {}.", drawForDate, e);
			throw new LotteryDrawException(drawForDate, e);
		}
	}
	
	private boolean sameDrawMonth(Date date, Date lotteryDrawOn) {
		if (lotteryDrawOn == null) {
			return false;
		}
		DateTime start = new DateTime(lotteryDrawOn.getTime());
		DateTime end = new DateTime(date.getTime());
		Months.monthsBetween(start, end);
		int months = Months.monthsBetween(start, end).getMonths();
	    return months == 0;
	}
	
	private LotteryDraw generateDraw(Date date) {
		LotteryDraw lotteryDraw = new LotteryDraw();
		lotteryDraw.setCombination(this.generateRandomCombination());
		lotteryDraw.setDrawOn(date);
		
		Double prize = (new Random()).nextDouble();
		prize *= 1000;
		prize = BigDecimal.valueOf(prize)
	    	.setScale(2, RoundingMode.HALF_UP)
	    	.doubleValue();
		lotteryDraw.setPrize(prize);
		
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
