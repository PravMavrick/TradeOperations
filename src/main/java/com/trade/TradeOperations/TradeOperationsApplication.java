package com.trade.TradeOperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradeOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeOperationsApplication.class, args);
	}

}
