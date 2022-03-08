package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelCreateService {
    private final RebelPersistenceGateway rebelPersistence;
    private final HeadQuartersPersistenceGateway headQuartersPersistence;

    public Rebel execute(Rebel rebel) throws HeadquarterNotFoundException {

    if (!headQuartersPersistence.existById(rebel.getId())){
        throw new HeadquarterNotFoundException();
    }
        return rebelPersistence.save(rebel);
    }
}
