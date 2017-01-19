package com.system.lottery.model.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.system.lottery.model.dao.exceptions.DuplicatedEntryException;
import com.system.lottery.model.dao.exceptions.NotFoundEntryException;
import com.system.lottery.model.dao.interfaces.LotteryDAO;
import com.system.lottery.model.db.PseudoDB;
import com.system.lottery.model.lib.bean.LotteryDraw;

@Service
public class LotteryDAOImpl implements LotteryDAO {
	
	private PseudoDB db = PseudoDB.getInstance();

	@Override
	public List<LotteryDraw> findAll() {
		return this.findAll(null);
	}
	
	@Override
	public List<LotteryDraw> findAll(Integer limit) {
		if ((limit == null) || (limit == 0)) {
			return this.db.getLotteryDrows().values().stream().collect(Collectors.toList());
		}
		return this.db.getLotteryDrows().values().stream().limit(limit).collect(Collectors.toList());
	}
	
	@Override
	public LotteryDraw findByDate(@NotNull Date date) {
		return this.db.getLotteryDrows().get(date);
	}

	@Override
	public boolean save(Date date, LotteryDraw lotteryDraw) throws DuplicatedEntryException {
		if (hasEntry(date)) {
			throw new DuplicatedEntryException(date);
		}
		this.doSave(date, lotteryDraw);
		return hasEntry(date);
	}

	@Override
	public boolean update(Date date, LotteryDraw lotteryDraw) throws NotFoundEntryException {
		if (!hasEntry(date)) {
			throw new NotFoundEntryException(date);
		}
		this.doUpdate(date, lotteryDraw);
		return hasEntry(date);
	}

	@Override
	public boolean delete(Date date) throws NotFoundEntryException {
		if (!hasEntry(date)) {
			throw new NotFoundEntryException(date);
		}
		this.doDelete(date);
		return hasEntry(date);
	}
	
	private boolean hasEntry(Date date) {
		return this.db.getLotteryDrows().containsKey(date);
	}
	
	private void doSave(Date date, LotteryDraw lotteryDraw) {
		this.db.getLotteryDrows().put(date, lotteryDraw);
	}
	
	private void doUpdate(Date date, LotteryDraw lotteryDraw) {
		this.db.getLotteryDrows().replace(date, lotteryDraw);
	}
	
	private void doDelete(Date date) {
		this.db.getLotteryDrows().remove(date);
	}
}
