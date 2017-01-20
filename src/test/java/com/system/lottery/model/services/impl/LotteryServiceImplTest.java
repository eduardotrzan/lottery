package com.system.lottery.model.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.system.lottery.model.configuration.AppConfiguration;
import com.system.lottery.model.dao.interfaces.LotteryDAO;

public class LotteryServiceImplTest {
	
	@Mock
	private AppConfiguration appConfiguration;
	
	@Mock
	private LotteryDAO lotteryDAO;

	@InjectMocks
	private LotteryServiceImpl lotteryService;
	
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGenerateCombination_lowThanMaxDraw() {
		testCombination(3, 50);
	}
	
	@Test
	public void testGenerateCombination_sameThanMaxDraw() {
		testCombination(50, 50);
	}
	
	@Test
	public void testGenerateCombination_overThanMaxDraw() {
		testCombination(60, 50);
	}
	
	private void testCombination(int size, int maxNumber) {
		when(this.appConfiguration.getTotalNumberPerDraw()).thenReturn(size);
		when(this.appConfiguration.getTotalNumbersLottery()).thenReturn(maxNumber);
		List<Integer> combination = this.lotteryService.generateRandomCombination();
		assertThat(combination).isNotNull();
		assertThat(combination).hasSize(size);
		hasNotElementHigherThan(combination, maxNumber);
	}
	
	private void hasNotElementHigherThan(List<Integer> combination, int maxNumber) {
		boolean hasHigher = combination.parallelStream().anyMatch(number -> number > maxNumber);
		assertThat(hasHigher).isFalse();
	}
	
}
