package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.repositories;

import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.HeadquarterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeadQuarterRepository extends JpaRepository<HeadquarterEntity, Long> {
    @Query(value = "SELECT * FROM TB_HEADQUARTERS WHERE HEADQUARTER_NAME=?1", nativeQuery = true)
    Optional<HeadquarterEntity> getByName(String name);
}
