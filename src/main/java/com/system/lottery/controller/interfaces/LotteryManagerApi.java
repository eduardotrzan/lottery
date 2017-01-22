package com.system.lottery.controller.interfaces;

import java.util.Date;
import java.util.List;

import com.system.lottery.controller.lib.ws.LotteryDrawWS;
import com.system.lottery.controller.lib.ws.LotteryResultWS;
import com.system.lottery.controller.lib.ws.TicketWS;

public interface LotteryManagerApi {
	
	LotteryResultWS drawResult(TicketWS ticket);
	
	LotteryDrawWS getCurrentDraw();
	LotteryDrawWS getDrawFrom(Date date);
	List<LotteryDrawWS> getLatestYearDraws();
	List<LotteryDrawWS> getLatestQtdDraws(int quantity);

}