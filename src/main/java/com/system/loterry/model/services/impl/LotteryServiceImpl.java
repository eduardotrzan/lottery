package com.system.loterry.model.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.system.loterry.model.lib.bean.LotteryDraw;
import com.system.loterry.model.lib.bean.Ticket;
import com.system.loterry.model.services.interfaces.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);

	public LotteryDraw getLatestDrawResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isWinner(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
