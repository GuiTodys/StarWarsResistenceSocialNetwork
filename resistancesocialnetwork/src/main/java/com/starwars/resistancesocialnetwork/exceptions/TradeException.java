package com.starwars.resistancesocialnetwork.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TradeException extends RuntimeException {

    @Builder
    public TradeException(String message) {
        super(message);
    }
}
