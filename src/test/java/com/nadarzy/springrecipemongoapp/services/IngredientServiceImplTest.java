package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.converters.IngredientCommandToIngredient;
import com.nadarzy.springrecipemongoapp.converters.IngredientToIngredientCommand;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.model.Ingredient;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.reactive.RecipeReactiveRepository;
import com.nadarzy.springrecipemongoapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

  @Mock
  RecipeReactiveRepository recipeRepository;
  IngredientService ingredientService;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;

  @Mock
  UnitOfMeasureReactiveRepository unitOfMeasureRepository;

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


    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

    // then
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

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
    command.setUom(new UnitOfMeasureCommand());
    command.getUom().setId("1234");



    Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId("3");

    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
    when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

    // when
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

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



    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
    when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));

    // when
    ingredientService.deleteIngredientById("1", "3");

    // then
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
}
