package com.nadarzy.springrecipemongoapp.repositories;

import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

   Optional<UnitOfMeasure> findByDescription(String description);
}
