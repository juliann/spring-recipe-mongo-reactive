package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;

public interface IngredientService {
  IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

  IngredientCommand saveIngredientCommand(IngredientCommand command);

  void deleteIngredientById(String recipeId, String ingredientId);
}
