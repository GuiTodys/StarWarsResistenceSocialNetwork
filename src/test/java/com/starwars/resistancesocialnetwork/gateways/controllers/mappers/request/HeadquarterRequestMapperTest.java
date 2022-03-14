package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.request.HeadQuarterRequestBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HeadquarterRequestMapperTest {

    HeadquarterRequestMapper mapper = HeadquarterRequestMapper.INSTANCE;

    @Test
    void toDomain_test_conversion_headquarter_request_to_domain() {
        HeadquarterRequest headquarterRequest = HeadQuarterRequestBuilder.builder().build().toRequest();
        Headquarter headquarterDomain = mapper.toDomain(headquarterRequest);

        Assertions.assertThat(headquarterDomain.getName()).isEqualTo(headquarterRequest.getName());
        Assertions.assertThat(headquarterDomain.getLocation()).isEqualTo(headquarterRequest.getLocation());
        Assertions.assertThat(headquarterDomain.getId()).isEqualTo(null);
    }

    @Test
    void toRequest_test_conversion_headquarter_domain_to_request() {
        Headquarter headquarterDomain = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterRequest headquarterRequest = mapper.toRequest(headquarterDomain);

        Assertions.assertThat(headquarterRequest.getName()).isEqualTo(headquarterRequest.getName());
        Assertions.assertThat(headquarterRequest.getLocation()).isEqualTo(headquarterRequest.getLocation());
    }
}