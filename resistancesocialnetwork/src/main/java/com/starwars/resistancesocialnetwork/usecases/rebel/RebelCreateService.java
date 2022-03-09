package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelCreateService {
    private final RebelPersistenceGateway rebelPersistence;

<<<<<<< Updated upstream
    public Rebel execute(Rebel rebel){
        return rebelPersistence.save(rebel);
=======
    public Rebel execute(Rebel rebel) throws HeadquarterNotFoundException {
        Headquarter headquarter = headQuartersPersistence.findById(rebel.getHeadquarterId()).orElseThrow(HeadquarterNotFoundException::new);
        return rebelPersistence.save(rebel, headquarter);
>>>>>>> Stashed changes
    }
}
