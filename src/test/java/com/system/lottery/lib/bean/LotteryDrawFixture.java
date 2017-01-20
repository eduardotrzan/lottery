package com.system.lottery.lib.bean;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.system.lottery.model.lib.bean.LotteryDraw;

public class LotteryDrawFixture {
	
	private final LotteryDraw lotteryDraw;

	public LotteryDrawFixture() {
		this.lotteryDraw = new LotteryDraw();
	}
	
	public LotteryDraw getLotteryDraw() {
		return this.lotteryDraw;
	}

	public LotteryDrawFixture basic() {
		combination(Arrays.asList(1, 2, 3, 4, 5, 6));
		drawOn((Date.from(LocalDate.of(2016, Month.JULY, 19).atStartOfDay(ZoneId.systemDefault()).toInstant())));
		prize(2.5);
		return this;
	}
	
	public LotteryDrawFixture combination(List<Integer> combination) {
		this.combination(combination);
		return this;
	}
	
	public LotteryDrawFixture drawOn(Date drawOn) {
		this.lotteryDraw.setDrawOn(drawOn);
		return this;
	}
	
	public LotteryDrawFixture prize(Double prize) {
		this.lotteryDraw.setPrize(prize);
		return this;
	}
}