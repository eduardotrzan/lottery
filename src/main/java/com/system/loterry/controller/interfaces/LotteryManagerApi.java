package com.system.loterry.controller.interfaces;

import com.system.loterry.model.lib.ws.LotteryResultWS;
import com.system.loterry.model.lib.ws.TicketWS;

public interface LotteryManagerApi {
	
	LotteryResultWS drawResult(TicketWS ticket);

}