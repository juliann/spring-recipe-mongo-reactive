package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.converters.RecipeCommandToRecipe;
import com.nadarzy.springrecipemongoapp.converters.RecipeToRecipeCommand;
import com.nadarzy.springrecipemongoapp.exceptions.NotFoundException;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;
  @Mock RecipeRepository recipeRepository;

  @Mock RecipeToRecipeCommand recipeToRecipeCommand;

  @Mock RecipeCommandToRecipe recipeCommandToRecipe;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    recipeService =
        new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  public void getRecipeByIdTestNotFound() {
    Optional<Recipe> recipeOptional = Optional.empty();
    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    Assertions.assertThrows(NotFoundException.class, () -> recipeService.findById("1"));
  }

  @Test
  public void getRecipes() {
    Recipe recipe = new Recipe();
    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(recipe);

    when(recipeRepository.findAll()).thenReturn(recipes);

    Set<Recipe> recipeSet = recipeService.getRecipes();
    Assertions.assertEquals(1, recipeSet.size());
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  public void getRecipeByIdTest() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId("1");
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById("1");

    assertNotNull("Null recipe returned", recipeReturned);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void testDeleteById() {
    // given
    String idToDelete = "2";
    // when
    recipeService.deleteById(idToDelete);

    // then
    verify(recipeRepository, times(1)).deleteById(anyString());
  }
}
