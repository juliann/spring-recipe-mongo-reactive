package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
  private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

  public UnitOfMeasureServiceImpl(
          UnitOfMeasureReactiveRepository unitOfMeasureRepository,
      UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
    this.unitOfMeasureRepository = unitOfMeasureRepository;
    this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
  }

  @Override
  public Flux<UnitOfMeasureCommand> listAllUoms() {

    return unitOfMeasureRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);

//    return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
//        .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//        .collect(Collectors.toSet());
  }
}
