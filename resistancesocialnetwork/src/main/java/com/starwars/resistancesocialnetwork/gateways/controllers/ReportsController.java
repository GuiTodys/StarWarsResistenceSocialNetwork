package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.starwars.resistancesocialnetwork.domains.Percentages;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.PercentagesResponseMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.PercentagesResponse;
import com.starwars.resistancesocialnetwork.usecases.reports.ReportPercentage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportPercentage reportPercentage;
    private final PercentagesResponseMapper percentagesResponseMapper = PercentagesResponseMapper.INSTANCE;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PercentagesResponse getRebelsPercentage(Pageable page){
        Percentages percentage= reportPercentage.execute(page);
        return percentagesResponseMapper.toResponse(percentage);
    }

}
