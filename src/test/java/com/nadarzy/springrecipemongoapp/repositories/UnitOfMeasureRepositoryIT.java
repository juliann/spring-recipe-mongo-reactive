package com.nadarzy.springrecipemongoapp.repositories;

import com.nadarzy.springrecipemongoapp.bootstrap.RecipeBootstrap;
import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

@DataMongoTest
public class UnitOfMeasureRepositoryIT {

  @Autowired UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired RecipeRepository recipeRepository;

  RecipeBootstrap recipeBootstrap;

  @BeforeEach
  public void setUp() throws Exception {
    categoryRepository.deleteAll();
    unitOfMeasureRepository.deleteAll();
    recipeRepository.deleteAll();
    recipeBootstrap =
        new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
    recipeBootstrap.onApplicationEvent(null);
  }

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
