package com.system.lottery.model.db;

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
	
	private TreeMap<Long, LotteryDraw> lotteryDrows;

	public TreeMap<Long, LotteryDraw> getLotteryDraws() {
		return lotteryDrows;
	}

	public void setLotteryDrows(TreeMap<Long, LotteryDraw> lotteryDrows) {
		this.lotteryDrows = lotteryDrows;
	}
}
