package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.converters.IngredientCommandToIngredient;
import com.nadarzy.springrecipemongoapp.converters.IngredientToIngredientCommand;
import com.nadarzy.springrecipemongoapp.model.Ingredient;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.repositories.reactive.RecipeReactiveRepository;
import com.nadarzy.springrecipemongoapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  private final RecipeReactiveRepository recipeRepository;
  private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

  public IngredientServiceImpl(
      IngredientToIngredientCommand ingredientToIngredientCommand,
      IngredientCommandToIngredient ingredientCommandToIngredient,
      RecipeReactiveRepository recipeRepository,
      UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.recipeRepository = recipeRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override
  public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

    return recipeRepository
        .findById(recipeId)
            .flatMapIterable(Recipe::getIngredients).filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)).single().map(
                    ingredient -> {
                      IngredientCommand ingredientCommand =
                              ingredientToIngredientCommand.convert(ingredient);
                      ingredientCommand.setRecipeId(recipeId);
                      return ingredientCommand;
                    });

//    old pre reactive code
//    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//    if (!recipeOptional.isPresent()) {
//
//      log.error("recipe id not found. Id: " + recipeId);
//    }
//
//    Recipe recipe = recipeOptional.get();
//
//    Optional<IngredientCommand> ingredientCommandOptional =
//        recipe.getIngredients().stream()
//            .filter(ingredient -> ingredient.getId().equals(ingredientId))
//            .map(ingredientToIngredientCommand::convert)
//            .findFirst();
//
//    if (!ingredientCommandOptional.isPresent()) {
//      // todo impl error handling
//      log.error("Ingredient id not found: " + ingredientId);
//    }
//
//    IngredientCommand ingredientCommand = ingredientCommandOptional.get();
//    ingredientCommand.setRecipeId(recipeId);
//
//    return Mono.just(ingredientCommandOptional.get());
  }

  @Override
  public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
    Recipe recipe = recipeRepository.findById(command.getRecipeId()).block();

    if (recipe == null) {

      //todo toss error if not found!
      log.error("Recipe not found for id: " + command.getRecipeId());
      return Mono.just(new IngredientCommand());
    } else {

      Optional<Ingredient> ingredientOptional = recipe
              .getIngredients()
              .stream()
              .filter(ingredient -> ingredient.getId().equals(command.getId()))
              .findFirst();

      if (ingredientOptional.isPresent()) {
        Ingredient ingredientFound = ingredientOptional.get();
        ingredientFound.setDescription(command.getDescription());
        ingredientFound.setAmount(command.getAmount());
        ingredientFound.setUom(unitOfMeasureRepository
                .findById(command.getUom().getId()).block());

        if (ingredientFound.getUom() == null) {
          new RuntimeException("UOM NOT FOUND");
        }
      } else {
        //add new Ingredient
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        recipe.addIngredient(ingredient);
      }

      Recipe savedRecipe = recipeRepository.save(recipe).block();

      Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
              .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
              .findFirst();

      //check by description
      if (!savedIngredientOptional.isPresent()) {
        //not totally safe... But best guess
        savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                .findFirst();
      }

      //todo check for fail

      //enhance with id value
      IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
      ingredientCommandSaved.setRecipeId(recipe.getId());

      return Mono.just(ingredientCommandSaved);
    }
}


  @Override
  public Mono<Void> deleteIngredientById(String recipeId, String idToDelete) {

    log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

    Recipe recipe = recipeRepository.findById(recipeId).block();

    if(recipe != null){

      log.debug("found recipe");

      Optional<Ingredient> ingredientOptional = recipe
              .getIngredients()
              .stream()
              .filter(ingredient -> ingredient.getId().equals(idToDelete))
              .findFirst();

      if(ingredientOptional.isPresent()){
        log.debug("found Ingredient");

        recipe.getIngredients().remove(ingredientOptional.get());
        recipeRepository.save(recipe);
      }
    } else {
      log.debug("Recipe Id Not found. Id:" + recipeId);
    }
    return Mono.empty();
  }
  }
//
//    log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);
//
//    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//    if (recipeOptional.isPresent()) {
//      Recipe recipe = recipeOptional.get();
//      log.debug("found recipe");
//
//      Optional<Ingredient> ingredientOptional =
//          recipe.getIngredients().stream()
//              .filter(ingredient -> ingredient.getId().equals(idToDelete))
//              .findFirst();
//
//      if (ingredientOptional.isPresent()) {
//        log.debug("found Ingredient");
//        Ingredient ingredientToDelete = ingredientOptional.get();
//        // ingredientToDelete.setRecipe(null);
//        recipe.getIngredients().remove(ingredientOptional.get());
//        recipeRepository.save(recipe);
//      }
//    } else {
//      log.debug("Recipe Id Not found. Id:" + recipeId);
//    }
//    return Mono.empty();
//  }

