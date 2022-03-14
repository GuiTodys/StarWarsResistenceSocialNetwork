package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.HeadquarterEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.RebelEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.mappers.HeadquarterEntityMapper;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.repositories.HeadQuarterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class HeadQuartersPersistenceGatewayImplTest {

    @Mock
    private HeadQuarterRepository headQuarterRepository;
    @InjectMocks
    HeadQuartersPersistenceGatewayImpl headQuarterPersistence;

    private final HeadquarterEntityMapper headquarterEntityMapper = HeadquarterEntityMapper.INSTANCE;

    @Test
    void save_when_inform_valid_headquarter_to_save_then_return_it() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterEntity headquarterEntity = headquarterEntityMapper.toEntity(headquarter);

        //when
        Mockito.when(headQuarterRepository.save(any(HeadquarterEntity.class)))
                .thenReturn(headquarterEntity);

        //then
        Headquarter saved = headQuarterPersistence.save(headquarter);

        Assertions.assertThat(saved.getName()).isEqualTo(headquarter.getName());
        Assertions.assertThat(saved.getLocation()).isEqualTo(headquarter.getLocation());
    }

    @Test
    void findById_when_inform_valid_headquarter_id_then_return_it() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterEntity headquarterEntity = headquarterEntityMapper.toEntity(headquarter);

        //when
        Mockito.when(headQuarterRepository.findById(headquarterEntity.getId()))
                .thenReturn(Optional.of(headquarterEntity));

        //then
        Optional<Headquarter> result = headQuarterPersistence.findById(headquarter.getId());
        Assertions.assertThat(result.isPresent()).isTrue();

        Assertions.assertThat(result.get().getName()).isEqualTo(headquarter.getName());
        Assertions.assertThat(result.get().getLocation()).isEqualTo(headquarter.getLocation());
    }

    @Test
    void findByName_when_inform_valid_headquarter_id_then_return_it() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterEntity headquarterEntity = headquarterEntityMapper.toEntity(headquarter);

        //when
        Mockito.when(headQuarterRepository.getByName(headquarterEntity.getName()))
                .thenReturn(Optional.of(headquarterEntity));

        //then
        Optional<Headquarter> result = headQuarterPersistence.findByName(headquarter.getName());
        Assertions.assertThat(result.isPresent()).isTrue();

        Assertions.assertThat(result.get().getName()).isEqualTo(headquarter.getName());
        Assertions.assertThat(result.get().getLocation()).isEqualTo(headquarter.getLocation());
    }
    @Test
    void existById_when_exists_id_return_true() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        //when
        Mockito.when(headQuarterRepository.existsById(headquarter.getId()))
                .thenReturn(true);

        //then
        boolean exists = headQuarterPersistence.existById(headquarter.getId());
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    void existById_when_non_exists_id_return_false() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();

        //when
        Mockito.when(headQuarterRepository.existsById(headquarter.getId()))
                .thenReturn(false);

        //then
        boolean exists = headQuarterPersistence.existById(headquarter.getId());
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    void findAll_should_return_a_page_of_headquarters() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        HeadquarterEntity headquarterEntity = headquarterEntityMapper.toEntity(headquarter);
        List<HeadquarterEntity> expectedContent = Collections.singletonList(headquarterEntity);
        PageRequest expectedPagination = PageRequest.of(1, 10);
        Page<HeadquarterEntity> expectedPagedResult = new PageImpl<>(expectedContent, expectedPagination, expectedContent.size());

        //when
        Mockito.when(headQuarterRepository.findAll(expectedPagination)).thenReturn(expectedPagedResult);

        //then
        Page<Headquarter> result = headQuarterPersistence.findAll(expectedPagination);

        Assertions.assertThat(result.getContent().size()).isEqualTo(expectedContent.size());
        Assertions.assertThat(result.getContent().get(0).getName()).isEqualTo(headquarter.getName());
        Assertions.assertThat(result.getContent().get(0).getLocation()).isEqualTo(headquarter.getLocation());
    }

    @Test
    void deleteById() {
        //given
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        //when
        Mockito.doNothing().when(headQuarterRepository).deleteById(headquarter.getId());
        //then
        headQuarterPersistence.deleteById(headquarter.getId());

        Mockito.verify(headQuarterRepository,Mockito.times(1)).deleteById(headquarter.getId());
    }
}