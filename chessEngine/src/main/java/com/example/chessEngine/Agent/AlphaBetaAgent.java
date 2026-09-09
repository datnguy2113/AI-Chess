package com.example.chessEngine.Agent;

import com.example.chessEngine.ChessLogic.Board;
import com.example.chessEngine.ChessLogic.ChessGameState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaBetaAgent extends Agent {

  public int depth = 2;

  public AlphaBetaAgent(String name, boolean isMyPieceBlack) {
    super.name = name;
    super.myPieceIsBlack = isMyPieceBlack;
  }

  public Map<String, Object> alphaBetaMinValue(ChessGameState state, double alpha, double beta, int currentDepth) {
    if (this.depth == currentDepth || state.isTerminal()) {
      return Map.of(
          "value", state.evaluate(this),
          "move", new int[] {0, 0, 0, 0}
      );
    }
    double v = Double.MAX_VALUE;
    int[] move =  new int[4];
    Agent player = state.getCurrentPlayer();
    List<int[]> actions = state.getActions(player.myPieceIsBlack);
    for (int[] action: actions) {
      ChessGameState newState = state.result(action);
      Map<String, Object> objMap = this.alphaBetaMaxValues(newState, alpha, beta, currentDepth + 1);
      double v2 = (double) objMap.get("value");
      int[] a2 = (int[]) objMap.get("move");
      if (v2 < v) {
        v = v2;
        move = action;
        beta = Math.min(beta, v);
      }
      if (v <= alpha) {
        return Map.of(
            "value", v,
            "move", move
        );
      }
    }
    return Map.of(
        "value", v,
        "move", move
    );
  }

  public Map<String, Object> alphaBetaMaxValues(ChessGameState state, double alpha, double beta, int currentDepth) {
    if (this.depth == currentDepth || state.isTerminal()) {
      return Map.of(
          "value", state.evaluate(this),
          "move", new int[4]
      );
    }
    double v = -Double.MAX_VALUE;
    int[] move =  new int[4];
    Agent player = state.getCurrentPlayer();
    List<int[]> actions = state.getActions(player.myPieceIsBlack);
    for (int[] action: actions) {
      ChessGameState newState = state.result(action);
      Map<String, Object> objectMap = this.alphaBetaMinValue(newState, alpha, beta, currentDepth + 1);
      double v2 = (double) objectMap.get("value");
      int[] a2 = (int[]) objectMap.get("move");
      if (v2 > v) {
        v = v2;
        move = action;
        alpha = Math.max(alpha, v);
      }
      if (v >= beta) {
        return Map.of(
            "value", v,
            "move", move
        );
      }
    }
    return Map.of(
        "value", v,
        "move", move
    );
  }

  @Override
  public int[] makeMove(Board board) {
    Board cloneBoard = board.clone();
    Agent randomPlayer = new RandomAgent(AgentName.RANDOM, !this.myPieceIsBlack);
    ChessGameState state = new ChessGameState(this, randomPlayer, cloneBoard);
    Map<String, Object> objectMap = this.alphaBetaMaxValues(state, -Double.MAX_VALUE, Double.MAX_VALUE, 0);
    return (int[]) objectMap.get("move");
  }
}
