package com.starwars.resistancesocialnetwork.gateways.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class ErrorResponse {
    private List<String> errorsMessage;
}
