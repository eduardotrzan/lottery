package com.system.lottery.model.dao.interfaces;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.system.lottery.model.dao.exceptions.DuplicatedEntryException;
import com.system.lottery.model.dao.exceptions.NotFoundEntryException;
import com.system.lottery.model.lib.bean.LotteryDraw;

public interface LotteryDAO {
	
	List<LotteryDraw> findAll();
	List<LotteryDraw> findAll(Integer limit);
	
	LotteryDraw findByDate(@NotNull Date date);
	
	boolean save(@NotNull Date date, @NotNull LotteryDraw lotteryDraw) throws DuplicatedEntryException;
	boolean update(@NotNull Date date, @NotNull LotteryDraw lotteryDraw) throws NotFoundEntryException;
	boolean delete(@NotNull Date date) throws NotFoundEntryException;
}
