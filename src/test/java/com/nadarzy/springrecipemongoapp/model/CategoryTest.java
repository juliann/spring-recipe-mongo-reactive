package com.nadarzy.springrecipemongoapp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CategoryTest {

  static Category category;

  @BeforeAll
  public static void setUp() {
    category = new Category();
  }

  @Test
  public void getId() {
    String idValue = "4";
    category.setId(idValue);
    Assertions.assertEquals(idValue, category.getId());
  }

  @Test
  public void getDescription() {}

  @Test
  public void getRecipes() {}
}
