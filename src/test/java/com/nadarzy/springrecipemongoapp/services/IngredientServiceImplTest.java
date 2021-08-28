package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.converters.IngredientCommandToIngredient;
import com.nadarzy.springrecipemongoapp.converters.IngredientToIngredientCommand;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.model.Ingredient;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.RecipeRepository;
import com.nadarzy.springrecipemongoapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

  @Mock RecipeRepository recipeRepository;
  IngredientService ingredientService;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;

  @Mock UnitOfMeasureRepository unitOfMeasureRepository;

  public IngredientServiceImplTest() {
    this.ingredientCommandToIngredient =
        new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    this.ingredientToIngredientCommand =
        new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  }

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    ingredientService =
        new IngredientServiceImpl(
            ingredientToIngredientCommand,
            ingredientCommandToIngredient,
            recipeRepository,
            unitOfMeasureRepository);
  }

  @Test
  public void findByRecipeIdAndIngredientId() {}

  @Test
  public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
    // given
    Recipe recipe = new Recipe();
    recipe.setId("1");

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId("1");

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId("1");

    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId("3");

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    // then
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3");

    // when
    Assertions.assertEquals("3", ingredientCommand.getId());
    verify(recipeRepository, times(1)).findById(anyString());
  }

  @Test
  public void testSaveRecipeCommand() throws Exception {
    // given
    IngredientCommand command = new IngredientCommand();
    command.setId("3");
    command.setRecipeId("2");

    Optional<Recipe> recipeOptional = Optional.of(new Recipe());

    Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId("3");

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
    when(recipeRepository.save(any())).thenReturn(savedRecipe);

    // when
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

    // then
    Assertions.assertEquals("3", savedCommand.getId());
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }

  @Test
  public void testDeleteById() throws Exception {
    // given
    Recipe recipe = new Recipe();
    Ingredient ingredient = new Ingredient();
    ingredient.setId("3");
    recipe.addIngredient(ingredient);

    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    // when
    ingredientService.deleteIngredientById("1", "3");

    // then
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
}
