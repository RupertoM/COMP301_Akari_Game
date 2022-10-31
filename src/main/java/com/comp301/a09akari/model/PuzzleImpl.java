package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;
  private final int width;
  private final int height;

  public PuzzleImpl(int[][] board) {
    this.board = board;
    this.height = board.length;
    this.width = board[0].length;
  }

  @Override
  public int getHeight() {
    return this.board.length;
  }

  @Override
  public int getWidth() {
    return this.board[0].length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }

    int cell_value = board[r][c];

    if (cell_value <= 4) {
      return CellType.CLUE;
    }
    if (cell_value == 5) {
      return CellType.WALL;
    }
    if (cell_value == 6) {
      return CellType.CORRIDOR;
    } else {
      return null;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    int cell_value = board[r][c];
    if (cell_value > 4) {
      throw new IllegalArgumentException();
    } else {
      return cell_value;
    }
  }
}
