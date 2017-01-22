package com.system.lottery.model.dao.impl;

import java.time.ZoneId;
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
			return this.db.getLotteryDraws().values().stream().collect(Collectors.toList());
		}
		return this.db.getLotteryDraws().values().stream().limit(limit).collect(Collectors.toList());
	}
	
	@Override
	public LotteryDraw findByDate(@NotNull Date drawForDate) {
		Date date = dateToBeginMonth(drawForDate);
		return this.db.getLotteryDraws().get(dateToTime(date));
	}
	
	@Override
	public LotteryDraw findLast() {
		if (this.db.getLotteryDraws().isEmpty()) {
			return null;
		}
		return this.db.getLotteryDraws().lastEntry().getValue();
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
		return this.db.getLotteryDraws().containsKey(dateToTime(date));
	}
	
	private void doSave(Date date, LotteryDraw lotteryDraw) {
		this.db.getLotteryDraws().put(dateToTime(date), lotteryDraw);
	}
	
	private void doUpdate(Date date, LotteryDraw lotteryDraw) {
		this.db.getLotteryDraws().replace(dateToTime(date), lotteryDraw);
	}
	
	private void doDelete(Date date) {
		this.db.getLotteryDraws().remove(dateToTime(date));
	}
	
	private long dateToTime(Date date) {
		Date standardDate = dateToBeginMonth(date);
		return standardDate.getTime();
	}
	
	private Date dateToBeginMonth(Date toBeginMonthDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		return Date.from(toBeginMonthDate
								.toInstant()
								.atZone(zoneId)
								.toLocalDate()
								.withDayOfMonth(1)
								.atStartOfDay(zoneId)
								.toInstant()
								);
	}
}
