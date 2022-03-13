package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.exceptions.TradeException;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.ItemRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.TradeRequest;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RebelTradeService {

    private final RebelGetService rebelGetService;
    private final RebelPersistenceGateway rebelPersistance;

    public Trade execute(Long id, Long target, Trade trade) {

        Rebel buyer = rebelGetService.getById(id);
        Rebel seller = rebelGetService.getById(target);
        if(buyer.getTraitor() || seller.getTraitor()){
            throw new TradeException("Not Allowed trade with Traitors");
        }

        List<Item> buyerInventory = buyer.getInventory();
        List<Item> sellerInventory = seller.getInventory();

        boolean buyerIsValid = buyerInventory.containsAll(trade.getBuyer());
        boolean sellerIsValid = sellerInventory.containsAll(trade.getSeller());

        if(!(buyerIsValid  && sellerIsValid)){
            throw new TradeException("Trade not Allowed");
        }
        Integer buyerPrice = trade.getBuyer().stream().map(Item::getValue)
                .reduce(0, (acc, cur) -> cur);

        Integer sellerPrice = trade.getBuyer().stream().map(Item::getValue)
                .reduce(0, (acc, cur) -> cur);

        if(!buyerPrice.equals(sellerPrice)){
            throw new TradeException("Trade not Allowed: incompatible values");
        }

        buyerInventory.removeAll(trade.getBuyer());
        sellerInventory.removeAll(trade.getSeller());

        buyerInventory.addAll(trade.getSeller());
        sellerInventory.addAll(trade.getBuyer());

        buyer.setInventory(buyerInventory);
        seller.setInventory(sellerInventory);

        rebelPersistance.saveVerifiedRebel(buyer);
        rebelPersistance.saveVerifiedRebel(seller);

        return Trade.builder().buyer(buyerInventory).seller(sellerInventory).build();

    }
}