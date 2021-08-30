package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.exceptions.NotFoundException;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
public class RecipeControllerTest {

  @Mock RecipeService recipeService;

  RecipeController controller;
  MockMvc mockMvc;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    controller = new RecipeController(recipeService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
  }

  @Test
  public void testGetRecipe() throws Exception {

    Recipe recipe = new Recipe();
    recipe.setId("1");

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));

    mockMvc
        .perform(get("/recipe/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/show"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testGetRecipeNotFound() throws Exception {

    when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
    mockMvc
        .perform(get("/recipe/1/show"))
        .andExpect(status().isNotFound())
        .andExpect(view().name("404error"));
  }

  //  @Test
  //  public void testGetRecipeNumberFormat() throws Exception {
  //
  //    mockMvc
  //        .perform(get("/recipe/a/show"))
  //        .andExpect(status().isBadRequest())
  //        .andExpect(view().name("400error"));
  //  }

  @Test
  public void testGetNewRecipeForm() throws Exception {

    mockMvc
        .perform(get("/recipe/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testPostNewRecipeForm() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId("2");

    when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

    mockMvc
        .perform(
            post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/show"));
  }

  @Test
  public void testGetUpdateView() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId("2");

    when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(command));

    mockMvc
        .perform(get("/recipe/1/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testDeleteAction() throws Exception {

    mockMvc
        .perform(get("/recipe/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));

    verify(recipeService, times(1)).deleteById(anyString());
  }

  @Test
  public void testPostNewRecipeFormValidationFail() throws Exception {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId("2");

    when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));

    mockMvc
        .perform(
            post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "3000"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("recipe"))
        .andExpect(view().name("recipe/recipeform"));
  }
}
