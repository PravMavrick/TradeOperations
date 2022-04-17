package com.trade.TradeOperations.Controller;

import com.trade.TradeOperations.Entity.Trade;
import com.trade.TradeOperations.Controller.Exception.ApiRequestException;
import com.trade.TradeOperations.Service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    private final TradeService tradeService;


    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/trade")
    public ResponseEntity<Trade> postTradeTranssmision(@RequestBody List<Trade> trade) throws ApiRequestException {
        System.out.println(trade.toString());
        tradeService.addTrade(trade);

        return new ResponseEntity<Trade>(HttpStatus.OK);
    }

}
