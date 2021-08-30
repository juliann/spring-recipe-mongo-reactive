package com.nadarzy.springrecipemongoapp.controllers;

import com.nadarzy.springrecipemongoapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

  public IndexController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  private final RecipeService recipeService;

  @RequestMapping({"/", "", "/index.html", "index"})
  public String getIndexPage(Model model) {
    log.debug("gettimg index page");

    model.addAttribute("recipes", recipeService.getRecipes());

    return "index";
  }
}
