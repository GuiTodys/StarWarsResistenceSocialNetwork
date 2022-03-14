package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.request.HeadQuarterRequestBuilder;
import com.starwars.resistancesocialnetwork.builders.response.HeadQuarterResponseBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.HeadquarterResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadquarterResponseMapperTest {

    HeadquarterResponseMapper mapper = HeadquarterResponseMapper.INSTANCE;

    @Test
    void toDomain_test_conversion_headquarter_response_to_domain() {
        HeadquarterResponse headquarterResponse = HeadQuarterResponseBuilder.builder().build().toResponse();
        Headquarter headquarterDomain = mapper.toDomain(headquarterResponse);

        Assertions.assertThat(headquarterDomain.getName()).isEqualTo(headquarterResponse.getName());
        Assertions.assertThat(headquarterDomain.getLocation()).isEqualTo(headquarterResponse.getLocation());
        Assertions.assertThat(headquarterDomain.getId()).isEqualTo(headquarterResponse.getId());
    }

    @Test
    void toRequest_test_conversion_headquarter_domain_to_response() {
        Headquarter headquarterDomain = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterResponse headquarterRequest = mapper.toResponse(headquarterDomain);

        Assertions.assertThat(headquarterRequest.getName()).isEqualTo(headquarterRequest.getName());
        Assertions.assertThat(headquarterRequest.getLocation()).isEqualTo(headquarterRequest.getLocation());
        Assertions.assertThat(headquarterRequest.getId()).isEqualTo(headquarterRequest.getId());
    }
}