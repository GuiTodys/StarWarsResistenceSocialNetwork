package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.RebelNotFoundException;
import com.starwars.resistancesocialnetwork.usecases.rebel.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RebelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RebelCreateService rebelCreateService;
    @MockBean
    private RebelDeleteService rebelDeleteService;
    @MockBean
    private RebelGetService rebelGetService;
    @MockBean
    private RebelUpdateService rebelUpdateService;
    @MockBean
    private RebelReportTraitorService rebelReportService;


    private final String API_URL_PATH = "/api/v1/rebels";
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void getRebelById_should_return_rebel_when_informed_a_valid_id() throws Exception {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        given(rebelGetService.getById(rebel.getId())).willReturn(rebel);

        MockHttpServletResponse response = mockMvc.perform(
                        get(API_URL_PATH + "/" + rebel.getId())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(rebel));
    }

    @Test
    void getRebelById_should_return_not_found_when_inform_invalid_id() throws Exception {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        given(rebelGetService.getById(rebel.getId())).willThrow(RebelNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + "/" + rebel.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRebels_should_return_a_page_of_rebels() throws Exception {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        List<Rebel> expectedContent = Collections.singletonList(rebel);
        PageRequest expectedPagination = PageRequest.of(1, 10);
        Page<Rebel> expectedPagedResult = new PageImpl<>(expectedContent, expectedPagination, expectedContent.size());

        given(rebelGetService.getAll(any())).willReturn(expectedPagedResult);

        mockMvc.perform(get(API_URL_PATH))
                .andExpect(status().isOk());
    }


    @Test
    void createRebel_when_inform_a_valid_rebel_then_create_it() throws Exception {
        Rebel expectedRebelToCreate = RebelDomainBuilder.builder().build().toDomain();
        String requestJson = mapper.writeValueAsString(expectedRebelToCreate);
        given(rebelCreateService.execute(any(Rebel.class))).willReturn(expectedRebelToCreate);

        MockHttpServletResponse response = mockMvc.perform(post(API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(requestJson);
    }

    @Test
    void createRebel_when_inform_a_rebel_without_an_requerid_field_then_given_a_error() throws Exception {
        Headquarter expectedHeadquarterToUpdate = HeadQuarterDomainBuilder.builder().build().toDomain();
        expectedHeadquarterToUpdate.setName("");

        String jsonRequest = mapper.writeValueAsString(expectedHeadquarterToUpdate);
        mockMvc.perform(post(API_URL_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateRebel_when_inform_a_valid_rebel_then_update_it() throws Exception {
        Rebel expectedRebelToUpdate = RebelDomainBuilder.builder().build().toDomain();
        given(rebelUpdateService.execute(any(Long.class), any(Rebel.class)))
                .willReturn(expectedRebelToUpdate);
        String jsonRequest = mapper.writeValueAsString(expectedRebelToUpdate);

        MockHttpServletResponse response = mockMvc.perform(put(API_URL_PATH + "/" + expectedRebelToUpdate.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isOk()).andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(expectedRebelToUpdate));
    }

    @Test
    void updateRebel_when_inform_a_invalid_rebel_then_return_not_found() throws Exception {
        Rebel expectedRebelToUpdate = RebelDomainBuilder.builder().build().toDomain();
        given(rebelUpdateService.execute(any(Long.class), any(Rebel.class)))
                .willThrow(RebelNotFoundException.class);
        String jsonRequest = mapper.writeValueAsString(expectedRebelToUpdate);

        mockMvc.perform(put(API_URL_PATH + "/" + expectedRebelToUpdate.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deleteById_when_inform_a_valid_id_then_delete_it() throws Exception {
        Rebel expectedRebelToDelete = RebelDomainBuilder.builder().build().toDomain();
        doNothing().when(rebelDeleteService).execute(expectedRebelToDelete.getId());

        mockMvc.perform(delete(API_URL_PATH + "/" + expectedRebelToDelete.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteById_when_inform_a_invalid_id_then_return_not_found() throws Exception {
        Rebel expectedRebelToDelete = RebelDomainBuilder.builder().build().toDomain();
        doThrow(RebelNotFoundException.class).when(rebelDeleteService)
                .execute(expectedRebelToDelete.getId());
        mockMvc.perform(delete(API_URL_PATH + "/" + expectedRebelToDelete.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void reportById_when_informed_valid_id_then_report_rebel() throws Exception {
        Rebel expectedRebelToReport = RebelDomainBuilder.builder().build().toDomain();
        Rebel expectedRebelReported = RebelDomainBuilder.builder().build().toDomain();
        expectedRebelReported.reportRebel();

        given(rebelReportService.execute(expectedRebelToReport.getId()))
                .willReturn(expectedRebelReported);

        MockHttpServletResponse response = mockMvc.perform(patch(API_URL_PATH + "/reportTraitor/" + expectedRebelToReport.getId())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(expectedRebelReported));
    }
    @Test
    void reportById_when_informed_invalid_id_then_return_not_found() throws Exception {
        Rebel expectedRebelToReport = RebelDomainBuilder.builder().build().toDomain();
        doThrow(RebelNotFoundException.class).when(rebelReportService)
                .execute(expectedRebelToReport.getId());
        mockMvc.perform(patch(API_URL_PATH + "/report/" + expectedRebelToReport.getId())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());

    }
}