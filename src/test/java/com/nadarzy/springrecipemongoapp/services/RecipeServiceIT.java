package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.converters.RecipeCommandToRecipe;
import com.nadarzy.springrecipemongoapp.converters.RecipeToRecipeCommand;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Disabled
public class RecipeServiceIT {

  public static final String NEW_DESCRIPTION = "New Description";

  @Autowired RecipeService recipeService;

  @Autowired RecipeRepository recipeRepository;

  @Autowired RecipeCommandToRecipe recipeCommandToRecipe;

  @Autowired RecipeToRecipeCommand recipeToRecipeCommand;

  @Transactional
  @Test
  public void testSaveOfDescription() throws Exception {
    // given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    // when
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

    // then
    Assertions.assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    Assertions.assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    Assertions.assertEquals(
        testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    Assertions.assertEquals(
        testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
  }
}
