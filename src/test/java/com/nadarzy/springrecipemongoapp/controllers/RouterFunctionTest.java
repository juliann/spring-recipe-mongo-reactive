package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.config.WebConfig;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

public class RouterFunctionTest {
  WebTestClient webTestClient;
  @Mock RecipeService recipeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    WebConfig webConfig = new WebConfig();
    RouterFunction<?> routerFunction = webConfig.routes(recipeService);

    webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
  }

  @Test
  void testGetRecipes() {
    when(recipeService.getRecipes()).thenReturn(Flux.just());
    webTestClient
        .get()
        .uri("/api/recipes")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void testGetRecipesWithData() {
    when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));
    webTestClient
        .get()
        .uri("/api/recipes")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Recipe.class);
  }
}
