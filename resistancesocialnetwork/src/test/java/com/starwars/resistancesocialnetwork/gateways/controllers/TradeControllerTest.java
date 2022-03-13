package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.TradeDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.exceptions.RebelNotFoundException;
import com.starwars.resistancesocialnetwork.exceptions.TradeException;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelTradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RebelTradeService rebelTradeService;

    private final String API_URL_PATH = "/api/v1/rebels/";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void trade_when_inform_valid_trade_then_trade_with_success() throws Exception {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();
        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();
        Rebel seller = RebelDomainBuilder.builder().build().toDomain();
        seller.setId(2L);
        given(rebelTradeService.execute(any(Long.class),any(Long.class),any(Trade.class))).willReturn(expectedTrade);
        String jsonRequest = mapper.writeValueAsString(expectedTrade);

        MockHttpServletResponse response = mockMvc.perform(post(API_URL_PATH + buyer.getId() + "/trade/" + seller.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                ).andExpect(status().isCreated())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(jsonRequest);
    }

    @Test
    void trade_when_inform_invalid_trade_then_trade_return_bad_request() throws Exception {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();
        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();
        Rebel seller = RebelDomainBuilder.builder().build().toDomain();
        seller.setId(2L);

        given(rebelTradeService.execute(any(Long.class),any(Long.class),any(Trade.class)))
                .willThrow(TradeException.class);
        String jsonRequest = mapper.writeValueAsString(expectedTrade);

        mockMvc.perform(post(API_URL_PATH + buyer.getId() + "/trade/" + seller.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                ).andExpect(status().isBadRequest());
    }

    @Test
    void trade_when_inform_invalid_rebel_id_then_trade_return_not_found() throws Exception {
        //given
        Trade expectedTrade = TradeDomainBuilder.builder().build().toDomain();
        Rebel buyer = RebelDomainBuilder.builder().build().toDomain();
        Rebel seller = RebelDomainBuilder.builder().build().toDomain();
        seller.setId(2L);

        given(rebelTradeService.execute(any(Long.class),any(Long.class),any(Trade.class)))
                .willThrow(RebelNotFoundException.class);
        String jsonRequest = mapper.writeValueAsString(expectedTrade);

        mockMvc.perform(post(API_URL_PATH + buyer.getId() + "/trade/" + seller.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isNotFound());
    }
}