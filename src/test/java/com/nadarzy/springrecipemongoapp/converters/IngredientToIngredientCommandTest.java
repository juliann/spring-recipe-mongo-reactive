package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.IngredientCommand;
import com.nadarzy.springrecipemongoapp.model.Ingredient;
import com.nadarzy.springrecipemongoapp.model.Recipe;
import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class IngredientToIngredientCommandTest {

  public static final Recipe RECIPE = new Recipe();
  public static final BigDecimal AMOUNT = new BigDecimal("1");
  public static final String DESCRIPTION = "Cheeseburger";
  public static final String UOM_ID = "2";
  public static final String ID_VALUE = "1";

  IngredientToIngredientCommand converter;

  @BeforeEach
  public void setUp() throws Exception {
    converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  }

  @Test
  public void testNullConvert() throws Exception {
    Assertions.assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    Assertions.assertNotNull(converter.convert(new Ingredient()));
  }

  @Test
  public void testConvertNullUOM() throws Exception {
    // given
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setRecipe(RECIPE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setUom(null);
    // when
    IngredientCommand ingredientCommand = converter.convert(ingredient);
    // then
    Assertions.assertNull(ingredientCommand.getUom());
    Assertions.assertEquals(ID_VALUE, ingredientCommand.getId());
    // assertEquals(RECIPE, ingredientCommand.get);
    Assertions.assertEquals(AMOUNT, ingredientCommand.getAmount());
    Assertions.assertEquals(DESCRIPTION, ingredientCommand.getDescription());
  }

  @Test
  public void testConvertWithUom() throws Exception {
    // given
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setRecipe(RECIPE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);

    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(UOM_ID);

    ingredient.setUom(uom);
    // when
    IngredientCommand ingredientCommand = converter.convert(ingredient);
    // then
    Assertions.assertEquals(ID_VALUE, ingredientCommand.getId());
    Assertions.assertNotNull(ingredientCommand.getUom());
    Assertions.assertEquals(UOM_ID, ingredientCommand.getUom().getId());
    // assertEquals(RECIPE, ingredientCommand.get);
    Assertions.assertEquals(AMOUNT, ingredientCommand.getAmount());
    Assertions.assertEquals(DESCRIPTION, ingredientCommand.getDescription());
  }
}
