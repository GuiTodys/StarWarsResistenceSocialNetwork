package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.RebelEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.mappers.RebelEntityMapper;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.repositories.RebelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
class RebelPersistenceGatewayImplTest {

    RebelEntityMapper rebelMapper = RebelEntityMapper.INSTANCE;

    @Mock
    RebelRepository repository;
    @InjectMocks
    RebelPersistenceGatewayImpl rebelPersistence;

    @Test
    void save_when_inform_valid_rebel_to_save_then_return_it() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        RebelEntity rebelEntity = rebelMapper.toEntity(rebel,headquarter);

        //when
        Mockito.when(repository.save(any(RebelEntity.class)))
                .thenReturn(rebelEntity);

        //then
        Rebel saved = rebelPersistence.save(rebel, headquarter);

        Assertions.assertThat(saved.getName()).isEqualTo(rebelEntity.getName());
        Assertions.assertThat(saved.getAge()).isEqualTo(rebelEntity.getAge());
        Assertions.assertThat(saved.getHeadquarterId()).isEqualTo(rebelEntity.getLocation().getId());
        Assertions.assertThat(saved.getInventory().toString()).isEqualTo(rebelEntity.getInventory().toString());
        Assertions.assertThat(saved.getReports()).isEqualTo(rebelEntity.getReports());
        Assertions.assertThat(saved.getTraitor()).isEqualTo(rebelEntity.getTraitor());
    }

    @Test
    void findById_when_inform_a_valid_id_then_return_optional_rebel() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        RebelEntity rebelEntity = rebelMapper.toEntity(rebel,headquarter);

        //when
        Mockito.when(repository.findById(rebel.getId()))
                .thenReturn(Optional.of(rebelEntity));

        //then
        Optional<Rebel> result = rebelPersistence.findById(rebel.getId());
        Assertions.assertThat(result.isPresent()).isTrue();

        Rebel saved = result.get();

        Assertions.assertThat(saved.getName()).isEqualTo(rebelEntity.getName());
        Assertions.assertThat(saved.getAge()).isEqualTo(rebelEntity.getAge());
        Assertions.assertThat(saved.getHeadquarterId()).isEqualTo(rebelEntity.getLocation().getId());
        Assertions.assertThat(saved.getInventory().toString()).isEqualTo(rebelEntity.getInventory().toString());
        Assertions.assertThat(saved.getReports()).isEqualTo(rebelEntity.getReports());
        Assertions.assertThat(saved.getTraitor()).isEqualTo(rebelEntity.getTraitor());
    }

    @Test
    void findById_when_inform_a_invalid_id_then_return_optional_empty() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();

        //when
        Mockito.when(repository.findById(rebel.getId()))
                .thenReturn(Optional.empty());

        //then
        Optional<Rebel> result = rebelPersistence.findById(rebel.getId());
        Assertions.assertThat(result.isPresent()).isFalse();
    }
    @Test
    void existById_when_exists_id_return_true() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        //when
        Mockito.when(repository.existsById(rebel.getId()))
                .thenReturn(true);

        //then
        boolean exists = rebelPersistence.existById(rebel.getId());
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    void existById_when_non_exists_id_return_false() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        //when
        Mockito.when(repository.existsById(rebel.getId()))
                .thenReturn(false);

        //then
        boolean exists = rebelPersistence.existById(rebel.getId());
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    void findAll_should_return_a_page_of_rebels() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        RebelEntity rebelEntity = rebelMapper.toEntity(rebel,headquarter);
        List<RebelEntity> expectedContent = Collections.singletonList(rebelEntity);
        PageRequest expectedPagination = PageRequest.of(1, 10);
        Page<RebelEntity> expectedPagedResult = new PageImpl<>(expectedContent, expectedPagination, expectedContent.size());

        //when
        Mockito.when(repository.findAll(expectedPagination)).thenReturn(expectedPagedResult);

        //then
        Page<Rebel> result = rebelPersistence.findAll(expectedPagination);

        Assertions.assertThat(result.getContent().size()).isEqualTo(expectedContent.size());
        Assertions.assertThat(result.getContent().get(0)).isEqualTo(rebel);
    }

    @Test
    void testFindAll_will_return_a_list_of_rebels() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();
        RebelEntity rebelEntity = rebelMapper.toEntity(rebel, headquarter);
        List<RebelEntity> rebels = List.of(rebelEntity);
        //when
        Mockito.when(repository.findAll()).thenReturn(rebels);
        //then
        List<Rebel> result = rebelPersistence.findAll();

        Assertions.assertThat(result.get(0)).isEqualTo(rebel);
    }

    @Test
    void deleteById() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        //when
        Mockito.doNothing().when(repository).deleteById(rebel.getId());
        //then
        rebelPersistence.deleteById(rebel.getId());

        Mockito.verify(repository,Mockito.times(1)).deleteById(rebel.getId());
    }
}