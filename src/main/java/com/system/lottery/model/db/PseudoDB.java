package com.system.lottery.model.db;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.system.lottery.model.lib.bean.LotteryDraw;

public final class PseudoDB {
	
	private static PseudoDB INSTANCE;
	
	private PseudoDB() {
		this.lotteryDrows = new TreeMap<>();
	}
	
	public static PseudoDB getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PseudoDB();
		}
		return INSTANCE;
	}
	
	private Map<Date, LotteryDraw> lotteryDrows;

	public Map<Date, LotteryDraw> getLotteryDrows() {
		return lotteryDrows;
	}

	public void setLotteryDrows(Map<Date, LotteryDraw> lotteryDrows) {
		this.lotteryDrows = lotteryDrows;
	}
}
