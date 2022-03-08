package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HeadQuartersPersistenceGatewayImpl implements HeadQuartersPersistenceGateway {
    @Override
    public Headquarter save(Headquarter headquarter) {
        return null;
    }

    @Override
    public Optional<Headquarter> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Page<Headquarter> findAll(Pageable page) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
