package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.resistancesocialnetwork.builders.domain.ItemsPerRebelDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.ReportsDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import com.starwars.resistancesocialnetwork.domains.Reports;
import com.starwars.resistancesocialnetwork.usecases.reports.ReportItemsPerRebelService;
import com.starwars.resistancesocialnetwork.usecases.reports.ReportReportsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReportsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportReportsService reportsService;
    @MockBean
    private ReportItemsPerRebelService reportItemsPerRebel;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String API_URL_PATH = "/api/v1/reports";
    private final String GENERAL_REPORTS_URL_PATH = "/generalReports";
    private final String ITEMS_PER_REBEL_URL_PATH = "/itemsPerRebel";

    @Test
    void getRebelsReports_when_request_generals_report_return_it() throws Exception {
        //given
        Reports expectedResponse = ReportsDomainBuilder.builder().build().toDomain();
        given(reportsService.execute()).willReturn(expectedResponse);

        MockHttpServletResponse response = mockMvc.perform(get(API_URL_PATH + GENERAL_REPORTS_URL_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(expectedResponse));
    }

    @Test
    void getItemsPerRebel_when_request_items_per_rebel_report_return_it() throws Exception {
        ItemsPerRebel expectedResponse = ItemsPerRebelDomainBuilder.builder().build().toDomain();
        given(reportItemsPerRebel.execute()).willReturn(expectedResponse);

        MockHttpServletResponse response = mockMvc.perform(get(API_URL_PATH + ITEMS_PER_REBEL_URL_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(expectedResponse));
    }
}