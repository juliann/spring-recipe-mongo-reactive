package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.CategoryCommand;
import com.nadarzy.springrecipemongoapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

  /**
   * @param source CategoryCommand
   * @return Category object
   */
  @Synchronized
  @Nullable
  @Override
  public Category convert(CategoryCommand source) {
    if (source == null) {
      return null;
    }

    final Category category = new Category();
    category.setId(source.getId());
    category.setDescription(source.getDescription());
    return category;
  }
}
