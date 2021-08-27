package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.commands.RecipeCommand;
import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.services.IngredientService;
import com.nadarzy.springrecipemongoapp.services.RecipeService;
import com.nadarzy.springrecipemongoapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UnitOfMeasureService unitOfMeasureService;

  public IngredientController(
      RecipeService recipeService,
      IngredientService ingredientService,
      UnitOfMeasureService unitOfMeasureService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }

  @GetMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("getting ingr for recipe " + recipeId);
    RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
    model.addAttribute("recipe", recipeCommand);

    return "recipe/ingredient/list";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
  public String showRecipeIngredient(
      @PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
    model.addAttribute(
        "ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    return "recipe/ingredient/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
  public String updateRecipeIngredient(
      @PathVariable String recipeId, @PathVariable String id, Model model) {
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
    return "recipe/ingredient/ingredientform";
  }

  @PostMapping("recipe/{recipeId}/ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

    log.debug("saved receipe id:" + savedCommand.getRecipeId());
    log.debug("saved ingredient id:" + savedCommand.getId());

    return "redirect:/recipe/"
        + savedCommand.getRecipeId()
        + "/ingredient/"
        + savedCommand.getId()
        + "/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/new")
  public String newRecipe(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
    // todo: exception if null
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(recipeId);
    model.addAttribute("ingredient", ingredientCommand);
    ingredientCommand.setUom(new UnitOfMeasureCommand());
    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
    return "recipe/ingredient/ingredientform";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
  public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
    log.debug("deleting ingredient id" + ingredientId);
    ingredientService.deleteIngredientById(recipeId, ingredientId);
    return "redirect:/recipe/" + recipeId + "/ingredients";
  }
}
