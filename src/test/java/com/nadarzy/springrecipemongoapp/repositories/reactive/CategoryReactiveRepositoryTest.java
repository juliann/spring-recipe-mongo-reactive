package com.nadarzy.springrecipemongoapp.repositories.reactive;

import com.nadarzy.springrecipemongoapp.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
    categoryReactiveRepository.deleteAll().block();
    }

  @Test
  void testSave() {
      Category category = new Category();
      category.setDescription("2. Breakfast");
    categoryReactiveRepository.save(category).block();

    Long count = categoryReactiveRepository.count().block();
    Assertions.assertEquals(1L, count);
  }

  @Test
  void testFindByDescription() {
      Category category = new Category();
      category.setDescription("2. Breakfast");
      categoryReactiveRepository.save(category).block();

      Category savedCategory = categoryReactiveRepository.findByDescription("2. Breakfast").block();
      Assertions.assertNotNull(savedCategory.getId());
  }
}