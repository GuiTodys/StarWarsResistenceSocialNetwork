package com.starwars.resistancesocialnetwork.gateways.persistance.impl.database;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RebelPersistenceGatewayImpl implements RebelPersistenceGateway {
    @Override
    public Rebel save(Rebel rebel) {
        return null;
    }

    @Override
    public Optional<Rebel> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Page<Rebel> findAll(Pageable page) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
