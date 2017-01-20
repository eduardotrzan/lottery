package com.system.lottery.controller.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.lottery.controller.interfaces.LotteryManagerApi;
import com.system.lottery.controller.lib.ws.LotteryResultWS;
import com.system.lottery.controller.lib.ws.TicketWS;
import com.system.lottery.controller.wrappers.WSBuilder;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.exceptions.LotteryDrawException;
import com.system.lottery.model.services.interfaces.LotteryService;

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
    public LotteryResultWS drawResult(TicketWS ticketWS) {
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
			return null; // change to WS error
		}
    }
}