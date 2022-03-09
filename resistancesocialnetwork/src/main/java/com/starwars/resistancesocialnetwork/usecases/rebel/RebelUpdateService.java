package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.exceptions.RebelNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebelUpdateService {
  private final RebelPersistenceGateway rebelPersistence;
  private final HeadQuartersPersistenceGateway headQuartersPersistence;

  public Rebel execute(Long id, Rebel rebel) throws RebelNotFoundException, HeadquarterNotFoundException {
    Headquarter headquarter = headQuartersPersistence.findById(rebel.getHeadquarterId()).orElseThrow(HeadquarterNotFoundException::new);
    Rebel result = rebelPersistence.findById(id).orElseThrow(RebelNotFoundException::new);
    BeanUtils.copyProperties(rebel, result, "id", "inventory");
    return rebelPersistence.save(result, headquarter);
  }


}
