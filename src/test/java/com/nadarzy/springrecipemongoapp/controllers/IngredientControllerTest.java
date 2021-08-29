package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.services.IngredientService;
import com.nadarzy.springrecipemongoapp.services.RecipeService;
import com.nadarzy.springrecipemongoapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
  @Mock RecipeService recipeService;
  @Mock IngredientService ingredientService;
  IngredientController controller;
  MockMvc mockMvc;
  @Mock UnitOfMeasureService unitOfMeasureService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testListIngredients() throws Exception {
    // given
    RecipeCommand recipeCommand = new RecipeCommand();
    when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

    // when
    mockMvc
        .perform(get("/recipe/1/ingredients"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/list"))
        .andExpect(model().attributeExists("recipe"));

    // then
    verify(recipeService, times(1)).findCommandById(anyString());
  }

  @Test
  public void testShowIngredient() throws Exception {
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();
    // when
    when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
        .thenReturn(Mono.just(ingredientCommand));
    // then
    mockMvc
        .perform(get("/recipe/1/ingredient/2/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/show"))
        .andExpect(model().attributeExists("ingredient"));
  }

  @Test
  public void testNewIngredientForm() throws Exception {
    // given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId("1");

    // when
    when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
    when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

    // then
    mockMvc
        .perform(get("/recipe/1/ingredient/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));

    verify(recipeService, times(1)).findCommandById(anyString());
  }

  @Test
  public void testUpdateIngredientForm() throws Exception {
    // given
    IngredientCommand ingredientCommand = new IngredientCommand();

    // when
    when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
        .thenReturn(Mono.just(ingredientCommand));
    when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

    // then
    mockMvc
        .perform(get("/recipe/1/ingredient/2/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));
  }

  @Test
  public void testSaveOrUpdate() throws Exception {
    // given
    IngredientCommand command = new IngredientCommand();
    command.setId("3");
    command.setRecipeId("2");

    // when
    when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(command));

    // then
    mockMvc
        .perform(
            post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
  }

  @Test
  public void testDeleteIngredient() throws Exception {
    when(ingredientService.deleteIngredientById(anyString(), anyString())).thenReturn(Mono.empty());
    mockMvc
        .perform(get("/recipe/2/ingredient/3/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/ingredients"));

    verify(ingredientService, times(1)).deleteIngredientById(anyString(), anyString());
  }
}
