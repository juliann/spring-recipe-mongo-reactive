package com.nadarzy.springrecipemongoapp.repositories;

import com.nadarzy.springrecipemongoapp.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

  public Optional<Category> findByDescription(String description);
}
