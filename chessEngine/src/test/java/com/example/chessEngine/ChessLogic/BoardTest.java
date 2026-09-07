package com.example.chessEngine.ChessLogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  private int ROW = 8;
  private int COL = 8;

  @Test
  public void creationTest() {
    Board board = new Board();
    assertEquals(board.getROWS(), ROW);
    assertEquals(board.getCOLS(), COL);
  }

}
