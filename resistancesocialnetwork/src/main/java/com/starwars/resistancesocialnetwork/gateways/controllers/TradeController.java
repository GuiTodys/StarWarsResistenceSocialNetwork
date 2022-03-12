package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request.TradeRequestMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.TradeResponseMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.TradeRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.TradeResponse;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelTradeService;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rebels/")
public class TradeController {

    private final RebelGetService rebelGetService;
    private final RebelTradeService rebelTradeService;
    private final TradeRequestMapper tradeRequestMapper = TradeRequestMapper.INSTANCE;
    private final TradeResponseMapper tradeResponseMapper = TradeResponseMapper.INSTANCE;

    @GetMapping(value = "{id}/trade}")
    public List<Item> getInventory(@PathVariable("id") Long id){
        Rebel found = rebelGetService.getById(id);
        return found.getInventory();
    }

    @PostMapping(value = "{id}/trade/{target}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public TradeResponse trade(
            @PathVariable("id") Long id,
            @PathVariable("target") Long target,
            @RequestBody TradeRequest trade){
        Trade tradeDomain = tradeRequestMapper.toDomain(trade);
        Trade traded = rebelTradeService.execute(id, target, tradeDomain);
        return tradeResponseMapper.toResponse(traded);
    }
}
