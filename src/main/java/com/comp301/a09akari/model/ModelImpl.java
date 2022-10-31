package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  List<ModelObserver> observers = new ArrayList<>();
  private PuzzleLibrary library;
  private int height;
  private int width;
  private Puzzle curr_puzzle;
  private boolean[][] lamps;
  private int puzzle_index;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.puzzle_index = 0;
    this.curr_puzzle = library.getPuzzle(puzzle_index);
    this.height = curr_puzzle.getHeight();
    this.width = curr_puzzle.getWidth();
    this.lamps = new boolean[height][width];
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lamps[r][c] == true) {
      return;
    }
    if (curr_puzzle.getCellType(r, c) == CellType.CORRIDOR) {
      lamps[r][c] = true;
      notifyObservers();
      return;
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (curr_puzzle.getCellType(r, c) == CellType.CORRIDOR) {
      lamps[r][c] = false;
      notifyObservers();
      return;
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    // current square
    if (lamps[r][c] == true) {
      return true;
    }
    // SOUTH
    for (int i = r + 1; (i < height); i++) {
      if (curr_puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(i, c)) {
          return true;
        }
      }
    }
    // NORTH
    for (int i = r - 1; i >= 0; i--) {
      if (curr_puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(i, c)) {
          return true;
        }
      }
    }
    // right
    for (int i = c + 1; i < width; i++) {
      if (curr_puzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, i)) {
          return true;
        }
      }
    }
    // left
    for (int i = c - 1; i >= 0; i--) {
      if (curr_puzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, i)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lamps[r][c] == false) {
      return false;
    }
    // SOUTH
    for (int i = r + 1; i < height; i++) {
      if (curr_puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(i, c)) {
          return true;
        }
      }
    }
    // NORTH
    for (int i = r - 1; i >= 0; i--) {
      if (curr_puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(i, c)) {
          return true;
        }
      }
    }
    // right
    for (int i = c + 1; i < width; i++) {
      if (curr_puzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, i)) {
          return true;
        }
      }
    }
    // left
    for (int i = c - 1; i >= 0; i--) {
      if (curr_puzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, i)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.curr_puzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.puzzle_index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    this.puzzle_index = index;
    this.curr_puzzle = this.library.getPuzzle(puzzle_index);
    this.height = this.curr_puzzle.getHeight();
    this.width = this.curr_puzzle.getWidth();
    this.lamps = new boolean[height][width];
    notifyObservers();
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return this.library.size();
  }

  @Override
  public void resetPuzzle() {
    this.lamps = new boolean[height][width];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    Puzzle c_puzzle = getActivePuzzle();
    for (int i = 0; i < c_puzzle.getHeight(); i++) {
      for (int j = 0; j < c_puzzle.getWidth(); j++) {
        if (c_puzzle.getCellType(i, j) == CellType.CLUE) {
          if (isClueSatisfied(i, j) == false) {
            return false;
          }
        }
        if (c_puzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if ((isLamp(i, j) && isLampIllegal(i, j)) || (isLit(i, j) == false)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (curr_puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int req_lamps = curr_puzzle.getClue(r, c);
    int count = 0;
    // up
    if (!((r - 1) < 0) && (curr_puzzle.getCellType(r - 1, c) == CellType.CORRIDOR)) {
      if (isLamp(r - 1, c)) {
        count++;
      }
    }
    // down
    if (((r + 1) < height && (curr_puzzle.getCellType(r + 1, c) == CellType.CORRIDOR))) {
      if (isLamp(r + 1, c)) {
        count++;
      }
    }
    // left
    if (!((c - 1) < 0) && (curr_puzzle.getCellType(r, c - 1) == CellType.CORRIDOR)) {
      if (isLamp(r, c - 1)) {
        count++;
      }
    }
    // right
    if (((c + 1) < width && (curr_puzzle.getCellType(r, c + 1) == CellType.CORRIDOR))) {
      if (isLamp(r, c + 1)) {
        count++;
      }
    }
    return (count == req_lamps);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }
}
