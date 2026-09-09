package com.example.chessEngine.ChessLogic;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  private final int ROW = 8;
  private final int COL = 8;
  private final int UNDER_BOUND = -1;
  private final int UPPER_BOUND = 8;
  private final int VALID_BOUND = 4;
  private final int DEFAULT_ROW = 3;
  private final int DEFAULT_COL = 3;

  @Test
  public void creationTest() {
    Board board = new Board();
    assertEquals(board.getROWS(), ROW);
    assertEquals(board.getCOLS(), COL);
  }

  @Test
  public void setPieceTest() {
    Board board = new Board();
    Piece piece = new Rook(0, 0, true);
    board.setPiece(0, 0, piece);

    assertEquals(piece, board.getPiece(0, 0));
  }

  @Test
  public void invalidSourceAndDestinationTest() {
    Board board = new Board();

    // out of bound test
    assertFalse(board.verifySourceAndDestination(UNDER_BOUND, VALID_BOUND, DEFAULT_ROW, DEFAULT_COL, true));
    assertFalse(board.verifySourceAndDestination(UPPER_BOUND, VALID_BOUND, DEFAULT_ROW, DEFAULT_COL, true));

    assertFalse(board.verifySourceAndDestination(VALID_BOUND, UNDER_BOUND, DEFAULT_ROW, DEFAULT_COL, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, UPPER_BOUND, DEFAULT_ROW, DEFAULT_COL, true));

    assertFalse(board.verifySourceAndDestination(UNDER_BOUND, UNDER_BOUND, DEFAULT_ROW, DEFAULT_COL, true));
    assertFalse(board.verifySourceAndDestination(UNDER_BOUND, UPPER_BOUND, DEFAULT_ROW, DEFAULT_COL, true));

    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, UNDER_BOUND, VALID_BOUND, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, UPPER_BOUND, VALID_BOUND, true));

    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, UNDER_BOUND, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, UPPER_BOUND, true));

    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, UPPER_BOUND, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, UNDER_BOUND, true));

    // test null cell
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, VALID_BOUND, true));

    // test correct piece
    board.setPiece(VALID_BOUND, VALID_BOUND, new Rook(VALID_BOUND, VALID_BOUND, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, VALID_BOUND, VALID_BOUND, false));

    // test move to valid cell that is either empty or opponent piece
    board.setPiece(DEFAULT_ROW, DEFAULT_COL, new Pawn(DEFAULT_ROW, DEFAULT_COL, true));
    assertFalse(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, DEFAULT_ROW, DEFAULT_COL, true));

    // test valid move
    board.setPiece(DEFAULT_ROW, DEFAULT_COL, new Pawn(DEFAULT_ROW, DEFAULT_COL, false));
    assertTrue(board.verifySourceAndDestination(VALID_BOUND, VALID_BOUND, DEFAULT_ROW, DEFAULT_COL, true));
  }


  @Test
  public void clearTest() {
    Board board = new Board();
    BoardInitializer.initialize(board);

    board.clear();
    for (int i = 0; i < this.ROW; i++) {
      for (int j = 0; j < this.COL; j++) {
        assertNull(board.getPiece(i, j));
      }
    }
  }

  @Test
  public void verifyAdjacentTest() {
    Board board = new Board();

    for (int i = 0; i < this.ROW; i++) {
      for (int j = 0; j < this.COL; j++) {

        // up and down
        assertTrue(board.verifyAdjacent(i, j, i - 1, j));
        assertTrue(board.verifyAdjacent(i, j, i + 1, j));

        // left and right
        assertTrue(board.verifyAdjacent(i, j, i, j - 1));
        assertTrue(board.verifyAdjacent(i, j, i, j + 1));

        // Not adjacent test
        assertFalse(board.verifyAdjacent(i, j, i - 2, j));
        assertFalse(board.verifyAdjacent(i, j, i + 2, j));

        assertFalse(board.verifyAdjacent(i, j, i, j - 2));
        assertFalse(board.verifyAdjacent(i, j, i, j + 2));
      }
    }
  }

  @Test
  public void verifyHorizontalTest() {
    Board board = new Board();

    // Check if different row
    assertFalse(board.verifyHorizontal(0, 1, 2, 1));

    for (int i = 0; i < this.ROW; i++) {
      for (int j = 0; j < this.COL; j++) {

        // Test all cell on the same row
        for (int k = 0; k < this.COL; k++) {
          if (k != j) {
            assertTrue(board.verifyHorizontal(i, j, i, k));
          }
        }

        // Invalid if there is a piece between
        for (int k = 0; k < j - 1; k++) {
          for (int m = k + 1; m < j; m++) {
            board.setPiece(i, m, new Pawn(i, m, true));
            assertFalse(board.verifyHorizontal(i, k, i, j));
            board.setPiece(i, m, null);
          }
        }
      }
    }
  }

  @Test
  public void cloneTest() {
    Board board = new Board();
    BoardInitializer.initialize(board);
    Board clone = board.clone();

    for (int i = 0; i < ROW; i++) {
      for (int j = 0; j < COL; j++) {
        Piece origin = board.getPiece(i, j);
        Piece cloneVersion = clone.getPiece(i, j);
        if (origin == null && cloneVersion != null) {
          fail();
        }
        if (origin != null && cloneVersion == null) {
          fail();
        }
        if (origin != null && cloneVersion != null) {
          boolean isTheSame = (origin.isBlack == cloneVersion.isBlack)
              && (origin.row == cloneVersion.row)
              && (origin.col == cloneVersion.col)
              && (origin.type.equals(cloneVersion.type))
              && (origin.representation == cloneVersion.representation);
          assertTrue(isTheSame);
        }
      }
    }
  }

  @Test
  public void verifyVerticalTest() {
    Board board = new Board();

    // Check if different row
    assertFalse(board.verifyVertical(0, 2, 5, 1));

    for (int j = 0; j < this.COL; j++) {
      for (int i = 0; i < this.ROW; i++) {

        // Test all cell on the same column
        for (int k = 0; k < this.ROW; k++) {
          if (k != i) {
            assertTrue(board.verifyVertical(i, j, k, j));
          }
        }
        // Invalid if there is a piece between
        for (int k = 0; k < i - 1; k++) {
          for (int m = k + 1; m < i; m++) {
            board.setPiece(m, j, new Pawn(m, j, true));
            assertFalse(board.verifyVertical(k, j, i, j));
            board.setPiece(m, j, null);
          }
        }
      }
    }
  }



}
