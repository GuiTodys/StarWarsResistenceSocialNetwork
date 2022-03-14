package com.starwars.resistancesocialnetwork.exceptions;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HeadquarterValidationException extends RuntimeException {
    @Builder
    public HeadquarterValidationException(Set<ConstraintViolation<Headquarter>> violations) {
        super(String.valueOf(violations));
    }
}
