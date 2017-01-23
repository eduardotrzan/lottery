package com.system.lottery.view.boot;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.system.lottery.model.configuration.AppConfiguration;
import com.system.lottery.model.services.interfaces.LotteryService;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.system.lottery.model", "com.system.lottery.controller"})
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    @Autowired
	private AppConfiguration appConfiguration;
    
    @Autowired
    private LotteryService lotteryService;
    
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
    	LOGGER.info("Running lottery system.");

    	LocalDate drawForDate = this.appConfiguration.getGenerateLotterySince().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate monthAgoFromNow = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay().toLocalDate();
    	try {
    		while (monthAgoFromNow.isAfter(drawForDate)) {
    			Date toDate = Date.from(drawForDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    			this.lotteryService.drawPrize(toDate);
    			drawForDate = drawForDate.plusMonths(1);
    		}
		} catch (Exception e) {
			LOGGER.error("Couldn't automatically draw lottery for date {}.", drawForDate, e);
		}
    	
    }

	public LotteryService getLotteryService() {
		return lotteryService;
	}

	public void setLotteryService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}
}
