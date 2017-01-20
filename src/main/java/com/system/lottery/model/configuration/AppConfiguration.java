package com.system.lottery.model.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
public class AppConfiguration {
	
	private int totalNumberPerDraw;
	
	private int totalNumbersLottery;
	
	public int getTotalNumberPerDraw() {
		return totalNumberPerDraw;
	}

	public void setTotalNumberPerDraw(int totalNumberPerDraw) {
		this.totalNumberPerDraw = totalNumberPerDraw;
	}

	public int getTotalNumbersLottery() {
		return totalNumbersLottery;
	}

	public void setTotalNumbersLottery(int totalNumbersLottery) {
		this.totalNumbersLottery = totalNumbersLottery;
	}
}
