package com.starwars.resistancesocialnetwork.usecases.headquarter;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.exception.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeadquarterUpdateService {

    private final HeadQuartersPersistenceGateway headQuartersPersistence;

    public Headquarter execute(Long id, Headquarter headquarter) throws HeadquarterNotFoundException {
        Headquarter result = headQuartersPersistence.findById(id).orElseThrow(HeadquarterNotFoundException::new);
        BeanUtils.copyProperties(headquarter, result, "id", "rebels");
        return headQuartersPersistence.save(result);
    }

}
