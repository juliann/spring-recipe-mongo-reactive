package com.nadarzy.springrecipemongoapp.repositories.reactive;

import com.nadarzy.springrecipemongoapp.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
