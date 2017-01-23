package com.system.lottery.controller.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

import com.system.lottery.model.services.interfaces.LotteryService;

public class LotteryManagerApiImplTest {
	
	@Mock
	private LotteryService lotteryService;

	@InjectMocks
	private LotteryManagerApiImpl lotteryManagerApi;
	
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
}
