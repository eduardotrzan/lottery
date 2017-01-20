package com.system.lottery.model.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
public class AppConfiguration {
	
	private int totalNumberPerDraw;
	
	private int totalNumbersLottery;
	
	private List<Double> prizeDistribution;
	
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

	public List<Double> getPrizeDistribution() {
		return prizeDistribution;
	}

	public void setPrizeDistribution(List<Double> prizeDistribution) {
		this.prizeDistribution = prizeDistribution;
	}
}
