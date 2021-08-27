package com.nadarzy.springrecipemongoapp.repositories;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {}
