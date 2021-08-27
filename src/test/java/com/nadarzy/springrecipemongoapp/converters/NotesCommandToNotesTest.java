package com.nadarzy.springrecipemongoapp.converters;

import com.nadarzy.springrecipemongoapp.commands.NotesCommand;
import com.nadarzy.springrecipemongoapp.model.Notes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesCommandToNotesTest {

  public static final String ID_VALUE = "1";
  public static final String RECIPE_NOTES = "Notes";
  NotesCommandToNotes converter;

  @BeforeEach
  public void setUp() throws Exception {
    converter = new NotesCommandToNotes();
  }

  @Test
  public void testNullParameter() throws Exception {
    Assertions.assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    Assertions.assertNotNull(converter.convert(new NotesCommand()));
  }

  @Test
  public void convert() throws Exception {
    // given
    NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(ID_VALUE);
    notesCommand.setRecipeNotes(RECIPE_NOTES);

    // when
    Notes notes = converter.convert(notesCommand);

    // then
    Assertions.assertNotNull(notes);
    Assertions.assertEquals(ID_VALUE, notes.getId());
    Assertions.assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
  }
}
