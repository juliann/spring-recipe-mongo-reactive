package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;
import com.nadarzy.springrecipemongoapp.model.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

  public static final String DESCRIPTION = "description";
  public static final String LONG_VALUE = "1";

  static UnitOfMeasureToUnitOfMeasureCommand converter;

  @BeforeAll
  public static void setUp() throws Exception {
    converter = new UnitOfMeasureToUnitOfMeasureCommand();
  }

  @Test
  public void testNullObjectConvert() throws Exception {
    Assertions.assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObj() throws Exception {
    Assertions.assertNotNull(converter.convert(new UnitOfMeasure()));
  }

  @Test
  public void convert() throws Exception {
    // given
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE);
    uom.setDescription(DESCRIPTION);
    // when
    UnitOfMeasureCommand uomc = converter.convert(uom);

    // then
    Assertions.assertEquals(LONG_VALUE, uomc.getId());
    Assertions.assertEquals(DESCRIPTION, uomc.getDescription());
  }
}
