package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.Puzzle;

public class ControllerImpl implements AlternateMvcController {
  ModelImpl model;

  public ControllerImpl(ModelImpl model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if ((model.getActivePuzzleIndex() < model.getPuzzleLibrarySize() - 1)) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() > 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int index = (int) Math.floor(Math.random() * (model.getPuzzleLibrarySize()));
    if (index == model.getActivePuzzleIndex()) {
      clickRandPuzzle();
    } else {
      model.setActivePuzzleIndex(index);
    }
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      }
      model.addLamp(r, c);
    }
  }

  @Override
  public void removeBulb(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      model.removeLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isIllegalLamp(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      return false;
    }
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }
}
