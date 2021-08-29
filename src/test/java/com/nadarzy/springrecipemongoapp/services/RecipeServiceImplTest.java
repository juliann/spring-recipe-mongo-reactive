package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.converters.RecipeCommandToRecipe;
import com.nadarzy.springrecipemongoapp.converters.RecipeToRecipeCommand;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;
  @Mock
  RecipeReactiveRepository recipeRepository;

  @Mock RecipeToRecipeCommand recipeToRecipeCommand;

  @Mock RecipeCommandToRecipe recipeCommandToRecipe;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    recipeService =
        new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  public void getRecipeCommandByIdTest() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId("1");

    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId("1");

    when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

    RecipeCommand commandById = recipeService.findCommandById("1").block();

    assertNotNull("Null recipe returned", commandById);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void getRecipesTest() {
    Recipe recipe = new Recipe();
    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(recipe);

    when(recipeRepository.findAll()).thenReturn(Flux.just(recipe));

    List<Recipe> recipesList = recipeService.getRecipes().collectList().block();
    Assertions.assertEquals(1, recipesList.size());
    verify(recipeRepository, times(1)).findAll();
    verify(recipeRepository, never()).findById(anyString());
  }

  @Test
  public void getRecipeByIdTest() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId("1");


    when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

    Recipe recipeReturned = recipeService.findById("1").block();

    assertNotNull("Null recipe returned", recipeReturned);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void testDeleteById() {
    // given
    String idToDelete = "2";
    when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
    // when
    recipeService.deleteById(idToDelete);

    // then
    verify(recipeRepository, times(1)).deleteById(anyString());
  }
}
