package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import com.nadarzy.springrecipemongoapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@Disabled
public class UnitOfMeasureServiceImplTest {
  UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand =
      new UnitOfMeasureToUnitOfMeasureCommand();
  UnitOfMeasureService service;

  @Mock UnitOfMeasureRepository unitOfMeasureRepository;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    service =
        new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
  }

  @Test
  public void listAllUoms() throws Exception {
    // given
    Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
    UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setId("1");
    unitOfMeasures.add(uom1);

    UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setId("2");
    unitOfMeasures.add(uom2);

    when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

    // when
    Set<UnitOfMeasureCommand> commands = service.listAllUoms();

    // then
    Assertions.assertEquals(2, commands.size());
    verify(unitOfMeasureRepository, times(1)).findAll();
  }
}
