package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
  Flux<Recipe> getRecipes();

  Mono<Recipe> findById(String id);

  Mono<RecipeCommand> findCommandById(String id);

  Mono<RecipeCommand>  saveRecipeCommand(RecipeCommand command);

  Mono<Object> deleteById(String idToDelete);
}
