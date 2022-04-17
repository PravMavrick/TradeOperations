package com.trade.TradeOperations;

import com.trade.TradeOperations.Controller.TradeController;
import com.trade.TradeOperations.Entity.Trade;
import com.trade.TradeOperations.Controller.Exception.ApiRequestException;
import com.trade.TradeOperations.Repository.TradeRepository;
import com.trade.TradeOperations.Service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradeOperationsApplicationTests {

	@Autowired
	private TradeService tradeService;

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeController tradeController;

	@Test
	void contextLoads() {
	}

	public static List<Trade> createTrades(int test){

	 List<Trade> tradeList = new ArrayList<>();

	 if (test == 1)
		 tradeList.add (new Trade("T1",1,"CP-1","B1",
			 LocalDate.of(2022,05,20),
			 LocalDate.of(2022,04,17)
			 ,"N"));
	else if (test == 2)
		 tradeList.add(new Trade("T2", 2, "CP-2", "B1",
				 LocalDate.of(2022, 05, 20),
				 LocalDate.of(2022, 04, 17)
				 , "N"));
    else if ( test == 3)
		 tradeList.add(new Trade("T2", 1, "CP-1", "B1",
				 LocalDate.of(2022, 05, 20),
				 LocalDate.of(2015, 02, 14)
				 , "N"));
	else {

	 }
	return tradeList;

	}

	//if all values are fine then trade store should get added into database
	@Test
	void ValidateTradeAndStoreIfSuccessful(){
	 tradeService.addTrade(createTrades(1));
		List<Trade> listTrade = tradeRepository.findAll();
		Assertions.assertEquals("T1",listTrade.stream().filter(t-> t.getTradeId().equals("T1")).collect(Collectors.toList()).get(0).getTradeId());
	}

	//If version is equals to existing version then update the record
	@Test
	void ValidateTradeVersionEqualsToExistingVersion()
	{
		Trade trade = new  Trade("T2",2,"CP-1","B1",
				LocalDate.of(2022,05,20),
				LocalDate.of(2022,04,17)
				,"N");

		tradeRepository.save(trade);

		tradeService.addTrade(createTrades(2));

		//record got updated and its matching with requested record
		List<Trade> listTrade = tradeRepository.findAll();
		Assertions.assertEquals("T2",listTrade.get(0).getTradeId());
		Assertions.assertEquals (2,listTrade.get(0).getVersion());
		Assertions.assertEquals ("CP-2",listTrade.get(0).getCounterPartyId());

	}


	//If version is equals to existing version then update the record
	@Test
	void ValidateTradeVersionLowerThanExistingVersion()
	{
		Trade trade = new  Trade("T2",2,"CP-1","B1",
				LocalDate.of(2022,05,20),
				LocalDate.of(2022,04,17)
				,"N");

		tradeRepository.save(trade);

		//catch the exception if version is lower than existing record version
		try {
			ResponseEntity<Trade> tradeResponseEntity = tradeController.postTradeTranssmision(createTrades(3));

			Assertions.assertEquals(HttpStatus.BAD_REQUEST, tradeResponseEntity.getStatusCode());
		} catch (ApiRequestException e){
			Assertions.assertEquals("Lower trade version received for store : " + trade.getTradeId(),
									e.getMessage());
		}

		//No change in database record
		List<Trade> listTrade = tradeRepository.findAll();
		Assertions.assertEquals(1,listTrade.size());
		Assertions.assertEquals("T2",listTrade.get(0).getTradeId());
		Assertions.assertEquals (2,listTrade.get(0).getVersion());
		Assertions.assertEquals ("CP-1",listTrade.get(0).getCounterPartyId());
	}



}
