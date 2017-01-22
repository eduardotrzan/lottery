package com.system.lottery.controller.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.lottery.controller.interfaces.LotteryManagerApi;
import com.system.lottery.controller.lib.ws.LotteryDrawWS;
import com.system.lottery.controller.lib.ws.LotteryResultWS;
import com.system.lottery.controller.lib.ws.TicketWS;
import com.system.lottery.controller.wrappers.WSBuilder;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.exceptions.LotteryDrawException;
import com.system.lottery.model.services.interfaces.LotteryService;
import com.system.lottery.model.utils.AppDateUtils;

@RestController
@RequestMapping("/lottery")
public class LotteryManagerApiImpl implements LotteryManagerApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryManagerApiImpl.class);
	
	@Autowired
    private LotteryService lotteryService;
	
	@RequestMapping(value="/drawResult"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
    public LotteryResultWS drawResult(@RequestBody TicketWS ticketWS) {
		try {
			LOGGER.info("Will draw result for ticket #{}", ticketWS.getNumber());
			LotteryDraw lotteryDraw = this.lotteryService.getLatestDrawResult();
			Ticket ticket = WSBuilder.toBean(ticketWS);
			Boolean isWinner = this.lotteryService.isWinner(ticket);
			LOGGER.info("Is winner? {}", isWinner);
			
			LotteryResultWS lotteryResultWS = WSBuilder.build(ticket.getDrawOn(), Arrays.asList(ticket));
			LOGGER.info("Informing the draw result from {} with prize of {}.", lotteryDraw.getDrawOn(), lotteryDraw.getPrize());
			return lotteryResultWS;
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
    }

	@RequestMapping(value="/currentDraw"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryDrawWS getCurrentDraw() {
		try {
			LotteryDraw lotteryDraw = this.lotteryService.getLatestDrawResult();
			if (lotteryDraw != null) {
				LOGGER.info("Last lottery draw was {}.", lotteryDraw.toString());
				return WSBuilder.toLotteryDrawWS(lotteryDraw);
			} else {
				LOGGER.info("No lottery draw happened to {}", AppDateUtils.formattedDate(new Date()));
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@RequestMapping(value="/drawFrom"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryDrawWS getDrawFrom(Date date) {
		try {
			LotteryDraw lotteryDraw = this.lotteryService.getDrawFrom(date);
			if (lotteryDraw != null) {
				LOGGER.info("Last lottery draw was {}.", lotteryDraw.toString());
				return WSBuilder.toLotteryDrawWS(lotteryDraw);
			} else {
				LOGGER.info("No lottery draw happened to {}", AppDateUtils.formattedDate(new Date()));
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@Override
	public List<LotteryDrawWS> getLatestYearDraws() {
		try {
			List<LotteryDraw> lotteryDraws = this.lotteryService.getLatestYearDraws();
			if ((lotteryDraws != null) && !lotteryDraws.isEmpty()) {
				return WSBuilder.toLotteryDrawWS(lotteryDraws);
			} else {
				LOGGER.info("Couldn't get last 12 months draw.");
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@Override
	public List<LotteryDrawWS> getLatestQtdDraws(int quantity) {
		try {
			List<LotteryDraw> lotteryDraws = this.lotteryService.getLatestQtdDraws(quantity);
			if ((lotteryDraws != null) && !lotteryDraws.isEmpty()) {
				return WSBuilder.toLotteryDrawWS(lotteryDraws);
			} else {
				LOGGER.info("Couldn't get last {} months draw.", quantity);
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}
}