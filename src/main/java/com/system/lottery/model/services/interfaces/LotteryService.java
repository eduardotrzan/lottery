package com.system.lottery.model.services.interfaces;

import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;
import com.system.lottery.model.services.exceptions.LotteryDrawException;

public interface LotteryService {
	
	LotteryDraw getLatestDrawResult() throws LotteryDrawException ;
	
	Boolean isWinner(Ticket ticket); 
	
}
