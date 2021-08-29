package com.nadarzy.springrecipemongoapp.repositories.reactive;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;
    @BeforeEach
    void setUp() {
    recipeReactiveRepository.deleteAll().block();
    }
    @Test
    void testRecipeSave(){
        Recipe recipe = new Recipe();
        recipe.setDescription("Weihnachtsgebäck");

    recipeReactiveRepository.save(recipe).block();
    Long count = recipeReactiveRepository.count().block();
        Assertions.assertEquals(1l, count);
    }
}