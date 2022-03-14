package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import com.starwars.resistancesocialnetwork.utils.RebelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportItemsPerRebelService {

    private final RebelGetService rebelGetService;
    private final RebelUtils rebelUtils = new RebelUtils();

    public ItemsPerRebel execute() {
        List<Rebel> rebels = rebelGetService.getAll();
        List<Rebel> notTraitor = rebelUtils.getNotTraitors(rebels);

        List<Item> rebelItems = rebelUtils.getAllNotTraitorsItems(notTraitor);

        List<Item> filteredByWeapon = rebelItems.stream().filter(item -> item==Item.WEAPON).collect(Collectors.toList());
        List<Item> filteredByAmmo = rebelItems.stream().filter(item -> item==Item.AMMO).collect(Collectors.toList());
        List<Item> filteredByWater = rebelItems.stream().filter(item -> item==Item.WATER).collect(Collectors.toList());
        List<Item> filteredByFood = rebelItems.stream().filter(item -> item==Item.FOOD).collect(Collectors.toList());

        Float weaponPerRebel = rebelUtils.intToFloat(filteredByWeapon.size())/rebelUtils.intToFloat(notTraitor.size());
        Float ammoPerRebel = rebelUtils.intToFloat(filteredByAmmo.size())/rebelUtils.intToFloat(notTraitor.size());
        Float waterPerRebel = rebelUtils.intToFloat(filteredByWater.size())/rebelUtils.intToFloat(notTraitor.size());
        Float foodPerRebel = rebelUtils.intToFloat(filteredByFood.size())/rebelUtils.intToFloat(notTraitor.size());

        return ItemsPerRebel.builder().weaponsPerRebel(weaponPerRebel).ammoPerRebel(ammoPerRebel).waterPerRebel(waterPerRebel).foodPerRebel(foodPerRebel).build();
    }
}
