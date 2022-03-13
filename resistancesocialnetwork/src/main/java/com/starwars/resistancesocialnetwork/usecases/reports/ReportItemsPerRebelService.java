package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
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
public class ReportItemsPerRebelService {

    private final RebelGetService rebelGetService;

    public ItemsPerRebel execute() {
        List<Rebel> rebels = rebelGetService.getAll().stream().filter(rebel -> !rebel.getTraitor()).collect(Collectors.toList());

        List<Item> rebelItems = new ArrayList<>();

        rebels.stream().map(rebel -> rebel.getInventory()).forEach(inventory -> rebelItems.addAll(inventory));

        List<Item> filteredByWeapon = rebelItems.stream().filter(item -> item==Item.WEAPON).collect(Collectors.toList());
        List<Item> filteredByAmmo = rebelItems.stream().filter(item -> item==Item.AMMO).collect(Collectors.toList());
        List<Item> filteredByWater = rebelItems.stream().filter(item -> item==Item.WATER).collect(Collectors.toList());
        List<Item> filteredByFood = rebelItems.stream().filter(item -> item==Item.FOOD).collect(Collectors.toList());

        Float weaponPerRebel = Integer.valueOf(filteredByWeapon.size()).floatValue()/Integer.valueOf(rebels.size()).floatValue();
        Float ammoPerRebel = Integer.valueOf(filteredByAmmo.size()).floatValue()/Integer.valueOf(rebels.size()).floatValue();
        Float waterPerRebel = Integer.valueOf(filteredByWater.size()).floatValue()/Integer.valueOf(rebels.size()).floatValue();
        Float foodPerRebel = Integer.valueOf(filteredByFood.size()).floatValue()/Integer.valueOf(rebels.size()).floatValue();

        return ItemsPerRebel.builder().weaponsPerRebel(weaponPerRebel).ammoPerRebel(ammoPerRebel).waterPerRebel(waterPerRebel).foodPerRebel(foodPerRebel).build();
    }
}
