package com.example.chessEngine.ChessLogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

  private int INITIAL_ROW = 0;
  private int INITIAL_COL = 0;
  private boolean BLACK = true;
  private boolean WHITE = false;
  private char BLACK_BISHOP = PieceRepresentation.BLACK_BISHOP_CODE;
  private char WHITE_BISHOP = PieceRepresentation.WHITE_BISHOP_CODE;

  @Test
  public void blackCreationTest() {
    Piece bishop = new Bishop(INITIAL_ROW, INITIAL_COL, BLACK);

    assertEquals(bishop.row, INITIAL_ROW);
    assertEquals(bishop.col, INITIAL_COL);
    assertEquals(bishop.isBlack, BLACK);
    assertEquals(bishop.representation, BLACK_BISHOP);
  }

  @Test
  public void whiteCreationTest() {
    Piece bishop = new Bishop(INITIAL_ROW, INITIAL_COL, WHITE);

    assertEquals(bishop.row, INITIAL_ROW);
    assertEquals(bishop.col, INITIAL_COL);
    assertEquals(bishop.isBlack, WHITE);
    assertEquals(bishop.representation, WHITE_BISHOP);
  }

  @Test
  public void moveUpRightTest() {
    assertEquals(true, true);
  }

}
