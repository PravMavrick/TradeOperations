package com.trade.TradeOperations.Service;

import com.trade.TradeOperations.Entity.Trade;
import com.trade.TradeOperations.Controller.Exception.ApiRequestException;
import com.trade.TradeOperations.Repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    public void addTrade(List<Trade> tradeList) throws ApiRequestException {

        for (Trade trade: tradeList) {
            if (validateVersion(trade).equals("Reject"))
                throw new ApiRequestException("Lower trade version received for store : " + trade.getTradeId());
            else if (validateVersion(trade) == "Override" && !trade.getMaturityDate().isBefore(LocalDate.now())) {
                Trade existingTrade = tradeRepository.findById(trade.getTradeId()).orElse(null);
                existingTrade.setTradeId(trade.getTradeId());
                existingTrade.setVersion(trade.getVersion());
                existingTrade.setCounter_partyId(trade.getCounterPartyId());
                existingTrade.setBookId(trade.getBookId());
                existingTrade.setMaturityDate(trade.getMaturityDate());
                existingTrade.setCreatedDate(trade.getCreatedDate());
                existingTrade.setExpired(trade.getExpired());
                tradeRepository.save(existingTrade);
            } else if (validateVersion(trade).equals("Accept")) {
                if (trade.getMaturityDate().isBefore(LocalDate.now())) {
                } else
                    tradeRepository.save(trade);
            }
        }
    }

    public String validateVersion(Trade trade){

        Optional<Trade> oldTrade = tradeRepository.findById(trade.getTradeId());
        if(oldTrade.isPresent())
            if(trade.getVersion() < oldTrade.get().getVersion())
                return "Reject";
            else if(trade.getVersion() == oldTrade.get().getVersion())
                return "Override";
        return "Accept";
    }

    public void updateExpiryFlag() {
        tradeRepository.findAll().stream().forEach(curTrade -> {
            if(curTrade.getMaturityDate().isBefore(LocalDate.now())){
                curTrade.setExpired("Y");
                tradeRepository.save(curTrade);
                log.info("Expiry flag updated for trade : " + curTrade.getTradeId());
            }
        });
    }
}
