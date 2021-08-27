package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

  public static final String DESCRIPTION = "description";
  public static final String LONG_VALUE = "1";

  static UnitOfMeasureCommandToUnitOfMeasure converter;

  @BeforeEach
  public void setUp() throws Exception {
    converter = new UnitOfMeasureCommandToUnitOfMeasure();
  }

  @Test
  public void testNullParamter() throws Exception {
    Assertions.assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    Assertions.assertNotNull(converter.convert(new UnitOfMeasureCommand()));
  }

  @Test
  public void convert() throws Exception {
    // given
    UnitOfMeasureCommand command = new UnitOfMeasureCommand();
    command.setId(LONG_VALUE);
    command.setDescription(DESCRIPTION);

    // when
    UnitOfMeasure uom = converter.convert(command);

    // then
    Assertions.assertNotNull(uom);
    Assertions.assertEquals(LONG_VALUE, uom.getId());
    Assertions.assertEquals(DESCRIPTION, uom.getDescription());
  }
}
