package com.nadarzy.springrecipemongoapp.services;

import com.nadarzy.springrecipemongoapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
  Set<UnitOfMeasureCommand> listAllUoms();
}
