package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.exceptions.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request.HeadquarterRequestMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.HeadquarterResponseMapper;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterCreateService;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterDeleteService;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterGetService;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterUpdateService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HeadquarterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HeadquarterCreateService createService;
    @MockBean
    private HeadquarterDeleteService deleteService;
    @MockBean
    private HeadquarterGetService getService;
    @MockBean
    private HeadquarterUpdateService updateService;

    private final String API_URL_PATH = "/api/v1/headquarters";

    private final HeadquarterRequestMapper headquarterRequestMapper =
            HeadquarterRequestMapper.INSTANCE;
    private final HeadquarterResponseMapper headquarterResponseMapper =
            HeadquarterResponseMapper.INSTANCE;
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void getHeadquarterById_should_return_headquarter_when_informed_a_valid_id() throws Exception {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        given(getService.getById(headquarter.getId())).willReturn(headquarter);

        MockHttpServletResponse response = mockMvc.perform(
                        get(API_URL_PATH + "/" + headquarter.getId())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(headquarter));
    }

    @Test
    void getHeadquarterById_should_return_not_found_when_inform_invalid_id() throws Exception {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        given(getService.getById(headquarter.getId())).willThrow(HeadquarterNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + "/" + headquarter.getId())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllHeadquarters_should_return_a_page_of_headquarters() throws Exception {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        List<Headquarter> expectedContent = Collections.singletonList(headquarter);
        PageRequest expectedPagination = PageRequest.of(1,10);
        Page<Headquarter> expectedPagedResult = new PageImpl<>(expectedContent,expectedPagination,expectedContent.size());

        given(getService.getAll(any())).willReturn(expectedPagedResult);

        mockMvc.perform(get(API_URL_PATH))
                .andExpect(status().isOk());
    }

    @Test
    void createHeadquarter_when_inform_a_valid_headquarter_then_create_it() throws Exception {
        Headquarter expectedHeadquarterToCreate = HeadQuarterDomainBuilder.builder().build().toDomain();
        String requestJson = mapper.writeValueAsString(expectedHeadquarterToCreate);
        given(createService.execute(any(Headquarter.class))).willReturn(expectedHeadquarterToCreate);

        MockHttpServletResponse response = mockMvc.perform(post(API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(requestJson);
    }

    @Test
    void createHeadquarter_when_inform_a_headquarter_without_an_requerid_field_then_given_a_error() throws Exception {
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
    void updateHeadquarter_when_inform_a_valid_headquarter_then_update_it() throws Exception {
        Headquarter expectedHeadquarterToUpdate = HeadQuarterDomainBuilder.builder().build().toDomain();
        given(updateService.execute(any(Long.class),any(Headquarter.class)))
                .willReturn(expectedHeadquarterToUpdate);
        String jsonRequest = mapper.writeValueAsString(expectedHeadquarterToUpdate);

        MockHttpServletResponse response = mockMvc.perform(put(API_URL_PATH + "/" + expectedHeadquarterToUpdate.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isOk()).andReturn().getResponse();

        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(expectedHeadquarterToUpdate));
    }

    @Test
    void updateHeadquarter_when_inform_a_invalid_headquarter_then_return_not_found() throws Exception {
        Headquarter expectedHeadquarterToUpdate = HeadQuarterDomainBuilder.builder().build().toDomain();
        given(updateService.execute(any(Long.class),any(Headquarter.class)))
                .willThrow(HeadquarterNotFoundException.class);
        String jsonRequest = mapper.writeValueAsString(expectedHeadquarterToUpdate);

       mockMvc.perform(put(API_URL_PATH + "/" + expectedHeadquarterToUpdate.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isNotFound());
    }

    @Test
    void updateHeadquarter_when_inform_a_headquarter_without_an_requerid_field_then_given_a_error() throws Exception {
        Headquarter expectedHeadquarterToUpdate = HeadQuarterDomainBuilder.builder().build().toDomain();
        expectedHeadquarterToUpdate.setName("");

        String jsonRequest = mapper.writeValueAsString(expectedHeadquarterToUpdate);
        mockMvc.perform(put(API_URL_PATH + "/" + expectedHeadquarterToUpdate.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_when_inform_a_valid_headquarter_then_delete_it() throws Exception {
        Headquarter expectedHeadquarterToDelete = HeadQuarterDomainBuilder.builder().build().toDomain();
        doNothing().when(deleteService).execute(expectedHeadquarterToDelete.getId());

        mockMvc.perform(delete(API_URL_PATH + "/" + expectedHeadquarterToDelete.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteById_when_inform_a_invalid_headquarter_then_throws_exception() throws Exception {
        Headquarter expectedHeadquarterToDelete = HeadQuarterDomainBuilder.builder().build().toDomain();
        doThrow(HeadquarterNotFoundException.class).when(deleteService)
                .execute(expectedHeadquarterToDelete.getId());
        mockMvc.perform(delete(API_URL_PATH + "/" + expectedHeadquarterToDelete.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }




}