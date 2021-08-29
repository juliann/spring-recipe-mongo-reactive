package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
  Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

  Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

  Mono<Void> deleteIngredientById(String recipeId, String idToDelete);
}
