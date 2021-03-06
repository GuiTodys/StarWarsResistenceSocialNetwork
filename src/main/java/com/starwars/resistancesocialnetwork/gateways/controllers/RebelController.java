package com.starwars.resistancesocialnetwork.gateways.controllers;

import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request.HeadquarterRequestMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request.RebelRequestMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response.RebelResponseMapper;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.RebelResponse;
import com.starwars.resistancesocialnetwork.usecases.rebel.*;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContext;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rebels")
@AllArgsConstructor
public class RebelController {
  private final RebelRequestMapper rebelRequestMapper = RebelRequestMapper.INSTANCE;
  private final RebelResponseMapper rebelResponseMapper = RebelResponseMapper.INSTANCE;
  private final HeadquarterRequestMapper headquarterRequestMapper = HeadquarterRequestMapper.INSTANCE;
  private final RebelCreateService rebelCreateService;
  private final RebelDeleteService rebelDeleteService;
  private final RebelGetService rebelGetService;
  private final RebelUpdateService rebelUpdateService;
  private final RebelLocationUpdateService rebelLocationUpdateService;
  private final RebelReportTraitorService rebelReportService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public RebelResponse getRebelById(@PathVariable("id") Long id){
    Rebel foundById = rebelGetService.getById(id);
    return rebelResponseMapper.toResponse(foundById);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Page<RebelResponse> getAllRebels(Pageable page) {
    Page<Rebel> foundAll = rebelGetService.getAll(page);
    return new PageImpl<>(
        foundAll.getContent().stream()
            .map(rebelResponseMapper::toResponse)
            .collect(Collectors.toList()));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public RebelResponse createRebel(@Valid @RequestBody RebelRequest rebelRequest){
    Rebel rebel = rebelRequestMapper.toDomain(rebelRequest);
    Rebel saved = rebelCreateService.execute(rebel);
    return rebelResponseMapper.toResponse(saved);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public RebelResponse updateRebel(
      @PathVariable("id") Long id, @RequestBody RebelRequest rebelRequest){
    Rebel rebel = rebelRequestMapper.toDomain(rebelRequest);
    Rebel updated = rebelUpdateService.execute(id, rebel);
    return rebelResponseMapper.toResponse(updated);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable("id") Long id){
    rebelDeleteService.execute(id);
  }


  @PatchMapping(value = "/reportTraitor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public RebelResponse reportById(@PathVariable("id") Long id){
    Rebel reportedRebel = rebelReportService.execute(id);
    return rebelResponseMapper.toResponse(reportedRebel);
  }

  @PatchMapping(value = "/updateLocation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public RebelResponse updateHeadquarter(
          @PathVariable("id") Long id,
          @Valid @RequestBody HeadquarterRequest headquarterRequest){
    Headquarter headquarter = headquarterRequestMapper.toDomain(headquarterRequest);
    Rebel updatedRebel = rebelLocationUpdateService.execute(id, headquarter);
    return rebelResponseMapper.toResponse(updatedRebel);
  }
}
