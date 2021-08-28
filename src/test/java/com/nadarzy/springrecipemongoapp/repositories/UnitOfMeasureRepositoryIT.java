package com.nadarzy.springrecipemongoapp.repositories;

import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Disabled
public class UnitOfMeasureRepositoryIT {

  @Autowired UnitOfMeasureRepository unitOfMeasureRepository;

  @BeforeAll
  public static void setUp() throws Exception {}

  @Test
  public void findByDescription() {
    Optional<UnitOfMeasure> tableSpoonUomOptional =
        unitOfMeasureRepository.findByDescription("Tablespoon");
    Assertions.assertEquals("Tablespoon", tableSpoonUomOptional.get().getDescription());
  }

  @Test
  public void findByDescriptionCup() {
    Optional<UnitOfMeasure> tableSpoonUomOptional =
        unitOfMeasureRepository.findByDescription("Cup");
    Assertions.assertEquals("Cup", tableSpoonUomOptional.get().getDescription());
  }
}
