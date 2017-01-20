package com.system.lottery.model.db;

import java.util.Date;
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
	
	private TreeMap<Date, LotteryDraw> lotteryDrows;

	public TreeMap<Date, LotteryDraw> getLotteryDrows() {
		return lotteryDrows;
	}

	public void setLotteryDrows(TreeMap<Date, LotteryDraw> lotteryDrows) {
		this.lotteryDrows = lotteryDrows;
	}
}
