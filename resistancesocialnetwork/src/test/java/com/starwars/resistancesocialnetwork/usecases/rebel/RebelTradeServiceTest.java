package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.TradeDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.exceptions.TradeException;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterGetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RebelTradeServiceTest {

    @Mock
    RebelGetService rebelGetService;
    @Mock
    HeadquarterGetService headquarterGetService;
    @Mock
    RebelPersistenceGateway rebelPersistence;
    @InjectMocks
    RebelTradeService tradeService;

    @Test
    void execute_when_inform_a_valid_trade_and_ids_then_trade_with_success() {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();

        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();
        List<Item> buyerInventory = new ArrayList<>(buyer.getInventory());
        buyerInventory.addAll(expectedTrade.getBuyer());
        buyer.setInventory(buyerInventory);

        Rebel seller = RebelDomainBuilder.builder().id(2L).build().toDomain();
        List<Item> sellerInventory = new ArrayList<>(seller.getInventory());
        sellerInventory.addAll(expectedTrade.getSeller());
        seller.setInventory(sellerInventory);
        //when
        Mockito.when(rebelGetService.getById(buyer.getId())).thenReturn(buyer);
        Mockito.when(rebelGetService.getById(seller.getId())).thenReturn(seller);

        //then
        Trade traded = tradeService.execute(buyer.getId(), seller.getId(), expectedTrade);

        Assertions.assertThat(buyer.getInventory()).isEqualTo(traded.getBuyer());
        Assertions.assertThat(seller.getInventory()).isEqualTo(traded.getSeller());
    }

    @Test
    void execute_when_inform_a_traitor_to_trade_throws_exception() {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();

        Rebel buyer = RebelDomainBuilder.builder().traitor(true).build().toDomain();
        List<Item> buyerInventory = new ArrayList<>(buyer.getInventory());
        buyerInventory.addAll(expectedTrade.getBuyer());
        buyer.setInventory(buyerInventory);

        Rebel seller = RebelDomainBuilder.builder().id(2L).build().toDomain();
        List<Item> sellerInventory = new ArrayList<>(seller.getInventory());
        sellerInventory.addAll(expectedTrade.getSeller());
        seller.setInventory(sellerInventory);
        //when
        Mockito.when(rebelGetService.getById(buyer.getId())).thenReturn(buyer);
        Mockito.when(rebelGetService.getById(seller.getId())).thenReturn(seller);

        //then
        assertThrows(TradeException.class,
                () -> tradeService.execute(buyer.getId(), seller.getId(), expectedTrade));
    }
    @Test
    void execute_when_rebels_no_have_items_to_trade_throws_exception() {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();

        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();

        Rebel seller = RebelDomainBuilder.builder().id(2L).build().toDomain();
        List<Item> sellerInventory = new ArrayList<>(seller.getInventory());
        sellerInventory.addAll(expectedTrade.getSeller());
        seller.setInventory(sellerInventory);
        //when
        Mockito.when(rebelGetService.getById(buyer.getId())).thenReturn(buyer);
        Mockito.when(rebelGetService.getById(seller.getId())).thenReturn(seller);

        //then
        assertThrows(TradeException.class,
                () -> tradeService.execute(buyer.getId(), seller.getId(), expectedTrade));
    }
    @Test
    void execute_when_inform_invalid_values_throws_exception() {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder()
                .buyer(List.of(Item.WEAPON,Item.WATER))
                .seller(List.of(Item.FOOD,Item.FOOD,Item.FOOD,Item.FOOD,Item.FOOD))
                .build().toDomain();

        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();
        List<Item> buyerInventory = new ArrayList<>(buyer.getInventory());
        buyerInventory.addAll(expectedTrade.getBuyer());
        buyer.setInventory(buyerInventory);

        Rebel seller = RebelDomainBuilder.builder().id(2L).build().toDomain();
        List<Item> sellerInventory = new ArrayList<>(seller.getInventory());
        sellerInventory.addAll(expectedTrade.getSeller());
        seller.setInventory(sellerInventory);
        //when
        Mockito.when(rebelGetService.getById(buyer.getId())).thenReturn(buyer);
        Mockito.when(rebelGetService.getById(seller.getId())).thenReturn(seller);

        //then
        assertThrows(TradeException.class,
                () -> tradeService.execute(buyer.getId(), seller.getId(), expectedTrade));
    }
}