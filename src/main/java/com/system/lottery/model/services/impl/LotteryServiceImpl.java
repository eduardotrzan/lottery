package com.system.lottery.model.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.lottery.model.dao.exceptions.DuplicatedEntryException;
import com.system.lottery.model.dao.interfaces.LotteryDAO;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.interfaces.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService {
	
	@Autowired
	public LotteryDAO lotteryDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);

	public LotteryDraw getLatestDrawResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isWinner(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	public LotteryDraw drawPrize() {
		LOGGER.info("Drawing a new result...");
		Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		LotteryDraw lotteryDraw = this.generateDraw(date);
		try {
			boolean isSaved = lotteryDAO.save(date, lotteryDraw);
			if (isSaved) {
				return lotteryDraw;
			} else {
				throw // add new exception.
			}
		} catch (DuplicatedEntryException e) {
			LOGGER.error("It wasn't possible to draw a result for date {}.", date, e);
		}
	}
	
	private LotteryDraw generateDraw(Date date) {
		return null;
	}
	
}
