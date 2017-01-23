package com.system.lottery.controller.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	@Autowired
    private LotteryService lotteryService;
	
	@RequestMapping(value="/purchaseTicket"
			, method = RequestMethod.GET
			)
	@Override
	public TicketWS purchaseTicket(@RequestParam String name) {
		try {
			Ticket ticket = this.lotteryService.puchaseTicket(name);
			if (ticket != null) {
				LOGGER.info("A ticket was purchased as {}.", ticket.toString());
				return WSBuilder.toTicketWS(ticket);
			} else {
				LOGGER.info("No ticket was purchased under name {}", name);
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}
	
	@RequestMapping(value="/startDraw"
			, method = RequestMethod.GET
			)
	@Override
	public LotteryDrawWS startDraw() {
		try {
			LotteryDraw lotteryDraw = this.lotteryService.drawPrize();
			if (lotteryDraw != null) {
				LOGGER.info("A draw happened today {}.", lotteryDraw);
				return WSBuilder.toLotteryDrawWS(lotteryDraw);
			} else {
				LOGGER.info("It wasn't possible to draw a prize today.");
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}
	
	@RequestMapping(value="/startDrawForDate"
			, method = RequestMethod.GET
			)
	@Override
	public LotteryDrawWS startDrawForDate(@RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date drawOn) {
		try {
			LotteryDraw lotteryDraw = this.lotteryService.drawPrize(drawOn);
			if (lotteryDraw != null) {
				LOGGER.info("A draw happened for date {} and prize as {}.", drawOn, lotteryDraw);
				return WSBuilder.toLotteryDrawWS(lotteryDraw);
			} else {
				LOGGER.info("It wasn't possible to draw a prize for {}.", drawOn);
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}
	
	@RequestMapping(value="/currentDraw"
			, method = RequestMethod.GET
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
			, method = RequestMethod.GET
			)
	@Override
	public LotteryDrawWS getDrawFrom(@RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date date) {
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

	@RequestMapping(value="/latestYearDraws"
			, method = RequestMethod.GET
			)
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

	@RequestMapping(value="/latestQtdDraws"
			, method = RequestMethod.GET
			)
	@Override
	public List<LotteryDrawWS> getLatestQtdDraws(@RequestParam int quantity) {
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

	@RequestMapping(value="/verifyResult"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryResultWS verifyResult(@RequestBody TicketWS ticketWS) {
		try {
			LOGGER.info("Verifying result for ticket {}.", ticketWS);
			Ticket checkedTicket = this.lotteryService.checkWinner(WSBuilder.toBean(ticketWS));
			
			if (checkedTicket != null) {
				return WSBuilder.build(checkedTicket.getDrawOn(), Arrays.asList(checkedTicket));
			} else {
				LOGGER.info("The ticket verified isn't a winner.");
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@RequestMapping(value="/verifyResults"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryResultWS verifyResults(@RequestBody List<TicketWS> ticketWSs) {
		try {
			LOGGER.info("Verifying result for ticket {}.", ticketWSs);
			List<Ticket> checkedTickets = this.lotteryService.checkWinners(WSBuilder.toBean(ticketWSs));
			
			if ((ticketWSs != null) && (!ticketWSs.isEmpty())) {
				return WSBuilder.build(checkedTickets);
			} else {
				LOGGER.info("The tickets verified aren't winners.");
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@RequestMapping(value="/verifyResultOnDate"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryResultWS verifyResultOnDate(@RequestBody TicketWS ticketWS, @RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date drawOn) {
		try {
			LOGGER.info("Verifying result for ticket {} on draw date {}.", ticketWS, drawOn);
			Ticket checkedTicket = this.lotteryService.checkWinner(WSBuilder.toBean(ticketWS), drawOn);
			
			if (checkedTicket != null) {
				return WSBuilder.build(checkedTicket.getDrawOn(), Arrays.asList(checkedTicket));
			} else {
				LOGGER.info("The ticket verified isn't a winner for draw on {}.", drawOn);
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}

	@RequestMapping(value="/verifyResultsOnDate"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
	@Override
	public LotteryResultWS verifyResultsOnDate(@RequestBody List<TicketWS> ticketWSs, @RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date drawOn) {
		try {
			LOGGER.info("Verifying result for tickest {} on draw date {}.", ticketWSs, drawOn);
			List<Ticket> checkedTickets = this.lotteryService.checkWinners(WSBuilder.toBean(ticketWSs), drawOn);
			
			if ((ticketWSs != null) && (!ticketWSs.isEmpty())) {
				return WSBuilder.build(checkedTickets);
			} else {
				LOGGER.info("The tickets verified haven't winners for draw on {}.", drawOn);
				return null; // Change to WS Empty result msg;
			}
		} catch (LotteryDrawException lde) {
			LOGGER.error(lde.getMessage(), lde);
			return null; // change to WS error
		}
	}
}