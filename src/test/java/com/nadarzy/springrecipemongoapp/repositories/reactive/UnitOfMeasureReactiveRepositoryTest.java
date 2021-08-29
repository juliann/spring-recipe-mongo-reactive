package com.nadarzy.springrecipemongoapp.repositories.reactive;

import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    @BeforeEach
    void setUp() {
    unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void testSave(){
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("handfull");

    unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

    Long count = unitOfMeasureReactiveRepository.count().block();
        Assertions.assertEquals(1L, count);
    }
    @Test
    void findByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("handfulls");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription("handfulls").block();
    Assertions.assertEquals("handfulls", fetchedUOM.getDescription());
    }
}