package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.mappers;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.entity.HeadQuarterEntityBuilder;
import com.starwars.resistancesocialnetwork.builders.request.HeadQuarterRequestBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.HeadquarterEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadquarterEntityMapperTest {

    HeadquarterEntityMapper mapper = HeadquarterEntityMapper.INSTANCE;

    @Test
    void toDomain() {
        HeadquarterEntity headquarterEntity = HeadQuarterEntityBuilder.builder().build().toEntity();
        Headquarter headquarterDomain = mapper.toDomain(headquarterEntity);

        Assertions.assertThat(headquarterDomain.getName()).isEqualTo(headquarterEntity.getName());
        Assertions.assertThat(headquarterDomain.getLocation()).isEqualTo(headquarterEntity.getLocation());
    }

    @Test
    void toEntity() {
        Headquarter headquarterDomain = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterEntity headquarterEntity = mapper.toEntity(headquarterDomain);

        Assertions.assertThat(headquarterEntity.getName()).isEqualTo(headquarterEntity.getName());
        Assertions.assertThat(headquarterEntity.getLocation()).isEqualTo(headquarterEntity.getLocation());
    }

}