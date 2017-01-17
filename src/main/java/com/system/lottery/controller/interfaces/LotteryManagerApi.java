package com.system.lottery.controller.interfaces;

import com.system.lottery.model.lib.ws.LotteryResultWS;
import com.system.lottery.model.lib.ws.TicketWS;

public interface LotteryManagerApi {
	
	LotteryResultWS drawResult(TicketWS ticket);

}