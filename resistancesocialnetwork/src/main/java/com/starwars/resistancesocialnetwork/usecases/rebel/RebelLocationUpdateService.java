package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebelLocationUpdateService {

    private final RebelGetService rebelGetService;
    private final HeadQuartersPersistenceGateway headQuartersPersistence;
    private final RebelPersistenceGateway rebelPersistenceGateway;

    public Rebel execute(Long id, Headquarter headquarterToUpdate) {
        Rebel rebel = rebelGetService.getById(id);
        Optional<Headquarter> headquarter = headQuartersPersistence.findByName(headquarterToUpdate.getName());
        if (headquarter.isEmpty()){
            Headquarter saveHeadquarter = headQuartersPersistence.save(headquarterToUpdate);
            return rebelPersistenceGateway.save(rebel, saveHeadquarter);
        } else {
            return rebelPersistenceGateway.save(rebel, headquarter.get());
        }
    }
}
