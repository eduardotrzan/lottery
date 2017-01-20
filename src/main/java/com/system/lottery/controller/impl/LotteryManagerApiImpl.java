package com.system.lottery.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.lottery.controller.interfaces.LotteryManagerApi;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.ws.LotteryResultWS;
import com.system.lottery.model.lib.ws.TicketWS;
import com.system.lottery.model.services.exceptions.LotteryDrawException;
import com.system.lottery.model.services.interfaces.LotteryService;
import com.system.lottery.model.wrappers.WSBuilder;

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
			LotteryDraw lotteryDraw = lotteryService.getLatestDrawResult();
			Boolean isWinner = lotteryService.isWinner(WSBuilder.toBean(ticketWS));
			LOGGER.info("Is winner? {}", isWinner);
			LotteryResultWS lotteryResultWS = new LotteryResultWS();
			lotteryResultWS.setLotteryDraw(WSBuilder.toWS(lotteryDraw));
			lotteryResultWS.setWinner(isWinner);
			LOGGER.info("Informing the draw result from {} with prize of {}.", lotteryDraw.getDrawOn(), lotteryDraw.getPrize());
			return lotteryResultWS;
		} catch (LotteryDrawException lde) {
			return null; // change to WS error
		}
    }
}