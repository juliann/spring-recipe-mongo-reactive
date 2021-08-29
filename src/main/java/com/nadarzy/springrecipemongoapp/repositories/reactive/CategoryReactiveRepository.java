package com.nadarzy.springrecipemongoapp.repositories.reactive;

import com.nadarzy.springrecipemongoapp.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
  Mono<Category> findByDescription(String description);
}
