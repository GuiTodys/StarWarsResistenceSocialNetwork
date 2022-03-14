package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.domains.Reports;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import com.starwars.resistancesocialnetwork.utils.RebelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportReportsService {

    private final RebelGetService rebelGetService;
    private final RebelUtils rebelUtils;

    public Reports execute(){
        List<Rebel> rebels = rebelGetService.getAll();
        List<Rebel> traitors = rebelUtils.getTraitors();
        List<Rebel> notTraitors = rebelUtils.getNotTraitors();

        Float traitorsPercentage = rebelUtils.intToFloat(traitors.size()) / rebelUtils.intToFloat(rebels.size()) * 100;
        Float notTraitorsPercentage = rebelUtils.intToFloat(notTraitors.size()) / rebelUtils.intToFloat(rebels.size()) * 100;

        List<Item> traitorsItems = rebelUtils.getAllTraitorsItems(traitors);
        Integer traitorsPoints = rebelUtils.sumOfItemsPoints(traitorsItems);

        return Reports.builder().notTraitorsPercentage(notTraitorsPercentage).traitorsPercentage(traitorsPercentage).traitorsPoints(traitorsPoints).build();
    }
}
