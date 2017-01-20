package com.system.lottery.controller.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.system.lottery.lib.bean.LotteryDrawFixture;
import com.system.lottery.model.lib.bean.LotteryDraw;
import com.system.lottery.model.lib.ws.LotteryResultWS;
import com.system.lottery.model.lib.ws.TicketWS;
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
	
	@Test
	public void testDrawResult_nullTicket() {
		Exception e = (Exception) (catchThrowable(() -> lotteryManagerApi.drawResult(null)));
		assertThat(e).isNotNull();
		assertThat(e).isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void testDrawResult_winnerTrue() {
		try {
			LotteryDraw lotteryDraw = new LotteryDrawFixture().basic().getLotteryDraw();
			when(lotteryService.getLatestDrawResult()).thenReturn(lotteryDraw);
			
			TicketWS ticketWS = new TicketWS();
			ticketWS.setName("Test");
			ticketWS.setNumber(1l);
			
			when(lotteryService.isWinner(anyObject())).thenReturn(true);
			LotteryResultWS lotteryResultWS = lotteryManagerApi.drawResult(ticketWS);
			
			assertThat(lotteryResultWS).isNotNull();
			assertThat(lotteryResultWS.getWinner()).isNotNull();
			assertThat(lotteryResultWS.getWinner()).isTrue();
		} catch (Exception e) {
			fail(e.getMessage(), e);
		}
	}
	
	@Test
	public void testDrawResult_winnerFalse() {
		try {
			LotteryDraw lotteryDraw = new LotteryDrawFixture().basic().getLotteryDraw();
			when(lotteryService.getLatestDrawResult()).thenReturn(lotteryDraw);
			
			TicketWS ticketWS = new TicketWS();
			ticketWS.setName("Test");
			ticketWS.setNumber(1l);
			
			when(lotteryService.isWinner(anyObject())).thenReturn(false);
			LotteryResultWS lotteryResultWS = lotteryManagerApi.drawResult(ticketWS);
			
			assertThat(lotteryResultWS).isNotNull();
			assertThat(lotteryResultWS.getWinner()).isNotNull();
			assertThat(lotteryResultWS.getWinner()).isTrue();
		} catch (Exception e) {
			fail(e.getMessage(), e);
		}
	}
}
