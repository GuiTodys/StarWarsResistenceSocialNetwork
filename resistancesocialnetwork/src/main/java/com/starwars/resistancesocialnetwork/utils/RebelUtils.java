package com.starwars.resistancesocialnetwork.utils;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RebelUtils {

    public List<Rebel> getTraitors(List<Rebel> rebels){
        return rebels.stream().filter(rebel -> rebel.getTraitor()).collect(Collectors.toList());
    }

    public List<Rebel> getNotTraitors(List<Rebel> rebels){
        return rebels.stream().filter(rebel -> !rebel.getTraitor()).collect(Collectors.toList());
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
