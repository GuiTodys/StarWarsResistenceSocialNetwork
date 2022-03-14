package com.starwars.resistancesocialnetwork.utils;

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
public class RebelUtils {

    private final RebelGetService rebelGetService;

    public List<Rebel> getTraitors(){
        return rebelGetService.getAll().stream().filter(rebel -> rebel.getTraitor()).collect(Collectors.toList());
    }

    public List<Rebel> getNotTraitors(){
        return rebelGetService.getAll().stream().filter(rebel -> !rebel.getTraitor()).collect(Collectors.toList());
    }

    public Float intToFloat(int i){
        return Integer.valueOf(i).floatValue();
    }

    public List<Item> getAllTraitorsItems(List<Rebel> traitors){
        List<Item> traitorsItems = new ArrayList<>();
        traitors.stream().map(traitor -> traitor.getInventory()).forEach(traitorInventory -> traitorsItems.addAll(traitorInventory));
        return traitorsItems;
    }

    public List<Item> getAllNotTraitorsItems(List<Rebel> notTraitors){
        List<Item> notTraitorsItems = new ArrayList<>();
        notTraitors.stream().map(traitor -> traitor.getInventory()).forEach(traitorInventory -> notTraitorsItems.addAll(traitorInventory));
        return notTraitorsItems;
    }

    public Integer sumOfItemsPoints(List<Item> items){
        return items.stream().map(item -> item.getValue()).reduce(0,(acc,cur) -> acc+cur);
    }

}
