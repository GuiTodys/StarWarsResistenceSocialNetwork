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
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void createHeadquarter() {
    }

    @Test
    void updateHeadquarter() {
    }

    @Test
    void deleteById() {
    }
}