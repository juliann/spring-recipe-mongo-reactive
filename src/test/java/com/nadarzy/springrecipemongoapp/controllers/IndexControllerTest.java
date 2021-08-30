package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Disabled
public class IndexControllerTest {

  @Mock Model model;

  IndexController indexController;
  @Mock RecipeService recipeService;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    indexController = new IndexController(recipeService);
  }

  @Test
  public void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    when(recipeService.getRecipes()).thenReturn(Flux.empty());
    mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
  }

  @Test
  public void getIndexPage() {

    // given
    Set<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());
    Recipe e = new Recipe();
    e.setId("2");
    recipes.add(e);

    when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

    ArgumentCaptor<List<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(List.class);

    String viewName = indexController.getIndexPage(model);

    Assertions.assertEquals("index", viewName);
    verify(recipeService, times(1)).getRecipes();
    verify(model, times(1)).addAttribute(eq("recipes"), setArgumentCaptor.capture());

    List<Recipe> setInController = setArgumentCaptor.getValue();
    Assertions.assertEquals(2, setInController.size());
  }
}
