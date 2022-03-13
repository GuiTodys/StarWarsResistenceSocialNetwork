package com.starwars.resistancesocialnetwork.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TradeException extends RuntimeException {

    @Builder
    public TradeException(String message) {
        super(message);
    }
}
