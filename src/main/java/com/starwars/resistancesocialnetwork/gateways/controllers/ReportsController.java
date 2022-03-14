package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import com.starwars.resistancesocialnetwork.domains.Reports;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.ItemsPerRebelResponseMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.PercentagesResponseMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.ItemsPerRebelResponse;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.ReportResponse;
import com.starwars.resistancesocialnetwork.usecases.reports.ReportItemsPerRebelService;
import com.starwars.resistancesocialnetwork.usecases.reports.ReportReportsService;
import lombok.RequiredArgsConstructor;
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

    private final ReportReportsService reportsService;
    private final ReportItemsPerRebelService reportItemsPerRebel;
    private final PercentagesResponseMapper percentagesResponseMapper = PercentagesResponseMapper.INSTANCE;
    private final ItemsPerRebelResponseMapper itemsPerRebelResponseMapper = ItemsPerRebelResponseMapper.INSTANCE;

    @GetMapping(value = "/generalReports",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportResponse getRebelsReports(){
        Reports percentage= reportsService.execute();
        return percentagesResponseMapper.toResponse(percentage);
    }

    @GetMapping(value = "/itemsPerRebel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ItemsPerRebelResponse getItemsPerRebel(){
        ItemsPerRebel itemsPerRebel = reportItemsPerRebel.execute();
        return itemsPerRebelResponseMapper.toResponse(itemsPerRebel);
    }

}
