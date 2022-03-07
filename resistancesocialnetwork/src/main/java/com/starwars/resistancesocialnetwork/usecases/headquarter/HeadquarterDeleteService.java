package com.starwars.resistancesocialnetwork.usecases.headquarter;

import com.starwars.resistancesocialnetwork.exceptions.HeadquarterNotFoundException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeadquarterDeleteService {
  private final HeadQuartersPersistenceGateway headQuartersPersistence;

  public void execute(Long id) throws HeadquarterNotFoundException {
    if (!headQuartersPersistence.existById(id)) {
      throw new HeadquarterNotFoundException();
    }
    headQuartersPersistence.deleteById(id);
  }
}
