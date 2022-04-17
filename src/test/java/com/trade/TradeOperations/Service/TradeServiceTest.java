package com.trade.TradeOperations.Service;

import com.trade.TradeOperations.Entity.Trade;
import com.trade.TradeOperations.Repository.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    void setUp() {
    }

    public static List<Trade> createTrades(int test){

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(new Trade("T3", 3, "CP-3", "B2",
                    LocalDate.of(2014, 05, 20),
                    LocalDate.of(2022, 04, 17)
                    , "Y"));
        return tradeList;

    }

    //Trade store should not allow if Maturity date is less than today's date
    @Test
    void ValidateStoreShouldNotAllowIfMaturityDateIsLessThanTodaysDate()
    {
        tradeService.addTrade(createTrades(4));

        //No change in database record
        List<Trade> listTrade = tradeRepository.findAll();
        Assertions.assertEquals(0,listTrade.size());
    }
}