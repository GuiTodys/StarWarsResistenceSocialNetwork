package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.RebelNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelReportTraitorService {
    private final RebelPersistenceGateway rebelPersistence;
    private final RebelGetService rebelGetService;
    private final HeadquarterGetService headquarterGetService;

    public Rebel execute(Long id){
        Rebel foundRebel = rebelGetService.getById(id);
        Headquarter foundRebelHeadquarter = headquarterGetService.getById(foundRebel.getHeadquarterId());
        foundRebel.reportRebel();
        return rebelPersistence.save(foundRebel, foundRebelHeadquarter);
    }
}
