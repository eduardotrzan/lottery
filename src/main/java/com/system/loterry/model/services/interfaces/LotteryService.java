package com.system.loterry.model.services.interfaces;

import com.system.loterry.model.lib.bean.LotteryDraw;
import com.system.loterry.model.lib.bean.Ticket;

public interface LotteryService {
	
	LotteryDraw getLatestDrawResult();
	
	Boolean isWinner(Ticket ticket); 
	
}
