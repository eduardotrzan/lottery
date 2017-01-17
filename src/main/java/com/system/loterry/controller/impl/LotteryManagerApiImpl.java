package com.system.loterry.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.loterry.controller.interfaces.LotteryManagerApi;
import com.system.loterry.model.lib.bean.LotteryDraw;
import com.system.loterry.model.lib.ws.LotteryResultWS;
import com.system.loterry.model.lib.ws.TicketWS;
import com.system.loterry.model.services.interfaces.LotteryService;
import com.system.loterry.model.wrappers.WSBuilder;

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
		LOGGER.info("Will draw result for ticket #{}", ticketWS.getNumber());
		LotteryDraw lotteryDraw = lotteryService.getLatestDrawResult();
		Boolean isWinner = lotteryService.isWinner(WSBuilder.toBean(ticketWS));
		LOGGER.info("Is winner? {}", isWinner);
		LotteryResultWS lotteryResultWS = new LotteryResultWS();
		lotteryResultWS.setLotteryDraw(WSBuilder.toWS(lotteryDraw));
		lotteryResultWS.setWinner(isWinner);
		LOGGER.info("Informing the draw result from {} with prize of {}.", lotteryDraw.getDrawOn(), lotteryDraw.getPrize());
		return lotteryResultWS;
    }
}