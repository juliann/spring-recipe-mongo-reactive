package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.model.Recipe;

import java.util.Set;

public interface RecipeService {
  Set<Recipe> getRecipes();

  Recipe findById(String id);

  RecipeCommand findCommandById(String id);

  RecipeCommand saveRecipeCommand(RecipeCommand command);

  void deleteById(String idToDelete);
}
