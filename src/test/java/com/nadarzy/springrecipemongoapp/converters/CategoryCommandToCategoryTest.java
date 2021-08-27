package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.CategoryCommand;
import com.nadarzy.springrecipemongoapp.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryCommandToCategoryTest {

  public static final String ID_VALUE = "1";
  public static final String DESCRIPTION = "description";
  CategoryCommandToCategory converter;

  @BeforeEach
  public void setUp() throws Exception {
    converter = new CategoryCommandToCategory();
  }

  @Test
  public void testNullObject() throws Exception {
    Assertions.assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    Assertions.assertNotNull(converter.convert(new CategoryCommand()));
  }

  @Test
  public void convert() throws Exception {
    // given
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID_VALUE);
    categoryCommand.setDescription(DESCRIPTION);

    // when
    Category category = converter.convert(categoryCommand);

    // then
    Assertions.assertEquals(ID_VALUE, category.getId());
    Assertions.assertEquals(DESCRIPTION, category.getDescription());
  }
}
