package com.nadarzy.springrecipemongoapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

  private String id;

  private String description;
  private BigDecimal amount;

  private Recipe recipe;

  private UnitOfMeasure uom;

  public Ingredient() {}

  public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
    this.description = description;
    this.amount = amount;
    this.uom = uom;
  }
}
