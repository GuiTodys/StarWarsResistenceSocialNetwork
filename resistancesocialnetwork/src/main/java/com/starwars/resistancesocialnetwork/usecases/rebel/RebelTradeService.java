package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.exceptions.TradeException;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterGetService;
import com.starwars.resistancesocialnetwork.utils.RebelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebelTradeService {

    private final RebelGetService rebelGetService;
    private final HeadquarterGetService headquarterGetService;
    private final RebelPersistenceGateway rebelPersistence;
    private final RebelUtils rebelUtils = new RebelUtils();

    public Trade execute(Long id, Long target, Trade trade) {

        Rebel buyer = rebelGetService.getById(id);
        Rebel seller = rebelGetService.getById(target);
        Headquarter buyerHeadquarter = headquarterGetService.getById(buyer.getHeadquarterId());
        Headquarter sellerHeadquarter = headquarterGetService.getById(seller.getHeadquarterId());

        if(buyer.getTraitor() || seller.getTraitor()){
            throw new TradeException("Not Allowed trade with Traitors");
        }
        validateRebelsItemsContent(trade.getBuyer(), buyer.getInventory());
        validateRebelsItemsContent(trade.getSeller(), seller.getInventory());
        verifyTradePrice(trade);
        swapItems(trade,buyer,seller);

        rebelPersistence.save(buyer, buyerHeadquarter);
        rebelPersistence.save(seller, sellerHeadquarter);

        return Trade.builder().buyer(buyer.getInventory()).seller(seller.getInventory()).build();
    }

    private void swapItems(Trade trade, Rebel buyer,Rebel seller){
        List<Item> buyerInventory = new ArrayList<>(buyer.getInventory());
        List<Item> sellerInventory = new ArrayList<>(seller.getInventory());

        buyerInventory.removeAll(trade.getBuyer());
        sellerInventory.removeAll(trade.getSeller());

        buyerInventory.addAll(trade.getSeller());
        sellerInventory.addAll(trade.getBuyer());

        buyer.setInventory(buyerInventory);
        seller.setInventory(sellerInventory);
    }

    private void verifyTradePrice(Trade trade) {
        Integer buyerPrice = rebelUtils.sumOfItemsPoints(trade.getBuyer());
        Integer sellerPrice = rebelUtils.sumOfItemsPoints(trade.getSeller());

        if(!buyerPrice.equals(sellerPrice)){
            throw new TradeException("Trade not Allowed: incompatible values");
        }
    }

    private void validateRebelsItemsContent(List<Item> tradeList, List<Item> traderInventory) {
        if(!traderInventory.containsAll(tradeList)){
            throw new TradeException("Trade not Allowed");
        }
    }
}
