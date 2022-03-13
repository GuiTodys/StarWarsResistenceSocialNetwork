package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.domains.Percentages;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportPercentage {

    private final RebelGetService rebelGetService;

    public Percentages execute(Pageable page){
        List<Rebel> rebels = rebelGetService.getAll();

        List<Rebel> traitors = rebels.stream().filter(rebel -> rebel.getTraitor()).collect(Collectors.toList());
        Float traitorsPercentage = Integer.valueOf(traitors.size()).floatValue() / Integer.valueOf(rebels.size()).floatValue() * 100;

        List<Rebel> notTraitors = rebels.stream().filter(rebel -> !rebel.getTraitor()).collect(Collectors.toList());
        Float notTraitorsPercentage = Integer.valueOf(notTraitors.size()).floatValue() / Integer.valueOf(rebels.size()).floatValue() * 100;


        return Percentages.builder().notTraitorsPercentage(notTraitorsPercentage).traitorsPercentage(traitorsPercentage).build();
    }
}
