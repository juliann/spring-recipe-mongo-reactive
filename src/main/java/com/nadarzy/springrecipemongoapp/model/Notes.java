package com.nadarzy.springrecipemongoapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

  private String id;

  private Recipe recipe;

  private String recipeNotes;

  public Notes() {}

  protected boolean canEqual(final Object other) {
    return other instanceof Notes;
  }
}
