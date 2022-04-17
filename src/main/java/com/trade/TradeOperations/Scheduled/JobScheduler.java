package com.trade.TradeOperations.Scheduled;

import com.trade.TradeOperations.Service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Autowired
    private final TradeService tradeService;


    public JobScheduler(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Scheduled(cron = "${trade.updateExpiry.schedule}")
    public void updateExpiryFlagForTrade(){
        tradeService.updateExpiryFlag();
    }
}
