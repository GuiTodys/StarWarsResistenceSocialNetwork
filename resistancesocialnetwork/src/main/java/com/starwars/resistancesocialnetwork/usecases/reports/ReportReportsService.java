package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.domains.Reports;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportReportsService {

    private final RebelGetService rebelGetService;

    public Reports execute(){
        List<Rebel> rebels = rebelGetService.getAll();

        List<Rebel> traitors = rebels.stream().filter(rebel -> rebel.getTraitor()).collect(Collectors.toList());
        Float traitorsPercentage = Integer.valueOf(traitors.size()).floatValue() / Integer.valueOf(rebels.size()).floatValue() * 100;

        List<Rebel> notTraitors = rebels.stream().filter(rebel -> !rebel.getTraitor()).collect(Collectors.toList());
        Float notTraitorsPercentage = Integer.valueOf(notTraitors.size()).floatValue() / Integer.valueOf(rebels.size()).floatValue() * 100;

        List<Item> traitorsItems = new ArrayList<>();
        traitors.stream().map(traitor -> traitor.getInventory()).forEach(traitorInventory -> traitorsItems.addAll(traitorInventory));
        Integer traitorsPoints = traitorsItems.stream().map(item -> item.getValue()).reduce(0,(acc,cur) -> acc+cur);


        return Reports.builder().notTraitorsPercentage(notTraitorsPercentage).traitorsPercentage(traitorsPercentage).traitorsPoints(traitorsPoints).build();
    }
}
