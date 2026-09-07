package com.example.chessEngine.ChessLogic;

import org.junit.jupiter.api.Test;
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

}
