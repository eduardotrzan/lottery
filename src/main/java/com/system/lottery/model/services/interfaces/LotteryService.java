package com.system.lottery.model.services.interfaces;

import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.bean.Ticket;

public interface LotteryService {
	
	LotteryDraw getLatestDrawResult();
	
	Boolean isWinner(Ticket ticket); 
	
}
