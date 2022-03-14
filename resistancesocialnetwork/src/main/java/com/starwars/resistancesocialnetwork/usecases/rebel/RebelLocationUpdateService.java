package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.HeadquarterValidationException;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RebelLocationUpdateService {

    private final RebelGetService rebelGetService;
    private final HeadQuartersPersistenceGateway headQuartersPersistence;
    private final RebelPersistenceGateway rebelPersistenceGateway;
    private final Validator validator;

    public Rebel execute(Long id, Headquarter headquarterToUpdate) {
        Rebel rebel = rebelGetService.getById(id);
        Optional<Headquarter> headquarter = headQuartersPersistence.findByName(headquarterToUpdate.getName());
        if (headquarter.isEmpty()){
            Headquarter saveHeadquarter = createNewHeadquarter(headquarterToUpdate);
            return rebelPersistenceGateway.save(rebel, saveHeadquarter);
        } else {
            return rebelPersistenceGateway.save(rebel, headquarter.get());
        }
    }

    private Headquarter createNewHeadquarter(Headquarter headquarterToCreate){
        Set<ConstraintViolation<Headquarter>> violations = validator.validate(headquarterToCreate);
        if (!violations.isEmpty()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (ConstraintViolation<Headquarter> constraintViolation : violations) {
//                stringBuilder.append(constraintViolation.getMessage());
//            }
            throw HeadquarterValidationException.builder().violations(violations).build();
        }
        return headQuartersPersistence.save(headquarterToCreate);
    }
}
