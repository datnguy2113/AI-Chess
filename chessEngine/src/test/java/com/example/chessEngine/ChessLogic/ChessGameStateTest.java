package com.example.chessEngine.ChessLogic;

import com.example.chessEngine.Agent.Agent;
import com.example.chessEngine.Agent.AlphaBetaAgent;
import com.example.chessEngine.Agent.RandomAgent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChessGameStateTest {

  @Test
  public void testInitialActionOfAlphaBeta() {
    Agent white = new RandomAgent("Random",true);
    Agent black = new AlphaBetaAgent("Alpha-Beta", false);
    Board board = new Board();
    BoardInitializer.initialize(board);
    ChessGameState state = new ChessGameState(white, black, board);
    assertEquals(state.evaluate(black), 0);

  }
}
