package com.starwars.resistancesocialnetwork.usecases.headquarter;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.exception.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeadquarterGetService {

    private final HeadQuartersPersistenceGateway headQuartersPersistence;

    public Headquarter getById(Long id) throws HeadquarterNotFoundException {
        Optional<Headquarter> result = headQuartersPersistence.findById(id);
        return result.orElseThrow(HeadquarterNotFoundException::new);
    }

    public Page<Headquarter> getAll(Pageable page){
        return headQuartersPersistence.findAll(page);
    }

}
