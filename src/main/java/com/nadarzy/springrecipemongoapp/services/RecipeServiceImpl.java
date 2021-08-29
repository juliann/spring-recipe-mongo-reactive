package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.converters.RecipeCommandToRecipe;
import com.nadarzy.springrecipemongoapp.converters.RecipeToRecipeCommand;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

  private final RecipeReactiveRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(
          RecipeReactiveRepository recipeRepository,
      RecipeCommandToRecipe recipeCommandToRecipe,
      RecipeToRecipeCommand recipeToRecipeCommand) {
    this.recipeRepository = recipeRepository;
    this.recipeCommandToRecipe = recipeCommandToRecipe;
    this.recipeToRecipeCommand = recipeToRecipeCommand;
  }

  @Override
  public Flux<Recipe> getRecipes() {



    return recipeRepository.findAll();
  }

  @Override
  public Mono<Recipe> findById(String id) {


    return recipeRepository.findById(id);
  }

  @Override
  public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

    return recipeRepository
        .save(recipeCommandToRecipe.convert(command))
        .map(recipeToRecipeCommand::convert);
  }

  @Override
  public Mono<RecipeCommand> findCommandById(String id) {
    return recipeRepository.findById(id)
            .map(recipe -> {
              RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

              recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
              });

              return recipeCommand;
            });
  }

  @Override
  public Mono<Object> deleteById(String idToDelete) {
    recipeRepository.deleteById(idToDelete).block();
    return Mono.empty();
  }
}
