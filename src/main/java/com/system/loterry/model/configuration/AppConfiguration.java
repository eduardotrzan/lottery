package com.system.loterry.model.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
public class AppConfiguration {
	
	private int totalNumberPerDraw;

	public int getTotalNumberPerDraw() {
		return totalNumberPerDraw;
	}

	public void setTotalNumberPerDraw(int totalNumberPerDraw) {
		this.totalNumberPerDraw = totalNumberPerDraw;
	}
}
