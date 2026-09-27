package com.example.chessEngine.ChessLogic;


import java.util.Random;

public final class BoardInitializer {

  private static final int ROWS = 8;
  private static final int COLS = 8;

  private static final String BK = PieceRepresentation.BLACK_KING;
  private static final String BN = PieceRepresentation.BLACK_KNIGHT;
  private static final String BB = PieceRepresentation.BLACK_BISHOP;
  private static final String BQ = PieceRepresentation.BLACK_QUEEN;
  private static final String BR = PieceRepresentation.BLACK_ROOK;
  private static final String BP = PieceRepresentation.BLACK_PAWN;

  private static final String WK = PieceRepresentation.WHITE_KING;
  private static final String WN = PieceRepresentation.WHITE_KNIGHT;
  private static final String WB = PieceRepresentation.WHITE_BISHOP;
  private static final String WQ = PieceRepresentation.WHITE_QUEEN;
  private static final String WR = PieceRepresentation.WHITE_ROOK;
  private static final String WP = PieceRepresentation.WHITE_PAWN;

  public static final String[][] congig = {
      {BR, BN, BB, BQ, BK, BB, BN, BR},
      {BP, BP, BP, BP, BP, BP, BP, BP},
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {WP, WP, WP, WP, WP, WP, WP, WP},
      {WR, WN, WB, WQ, WK, WB, WN, WR},
  };

  public static final Character[][] characterConfig = {
      {PieceRepresentation.BLACK_ROOK_CODE, PieceRepresentation.BLACK_KNIGHT_CODE, PieceRepresentation.BLACK_BISHOP_CODE, PieceRepresentation.BLACK_QUEEN_CODE, PieceRepresentation.BLACK_KING_CODE, PieceRepresentation.BLACK_BISHOP_CODE, PieceRepresentation.BLACK_KNIGHT_CODE, PieceRepresentation.BLACK_ROOK_CODE},
      {PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE, PieceRepresentation.BLACK_PAWN_CODE,},
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {null, null, null, null, null, null, null, null },
      {PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE, PieceRepresentation.WHITE_PAWN_CODE,},
      {PieceRepresentation.WHITE_ROOK_CODE, PieceRepresentation.WHITE_KNIGHT_CODE, PieceRepresentation.WHITE_BISHOP_CODE, PieceRepresentation.WHITE_QUEEN_CODE, PieceRepresentation.WHITE_KING_CODE, PieceRepresentation.WHITE_BISHOP_CODE, PieceRepresentation.WHITE_KNIGHT_CODE, PieceRepresentation.WHITE_ROOK_CODE}
  };



  public static void initialize(Board board) {

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        String pieceStr = congig[i][j];
        if (pieceStr != null) {
          char color = pieceStr.charAt(0);
          boolean isBlack = color == 'B';
          String pieceName = pieceStr.substring(1);
          switch (pieceName) {
            case PieceRepresentation.PAWN:
              board.setPiece(i, j, new Pawn(i, j, isBlack));
              break;
            case PieceRepresentation.ROOK:
              board.setPiece(i, j, new Rook(i, j, isBlack));
              break;
            case PieceRepresentation.KNIGHT:
              board.setPiece(i, j, new Knight(i, j, isBlack));
              break;
            case PieceRepresentation.BISHOP:
              board.setPiece(i, j, new Bishop(i, j, isBlack));
              break;
            case PieceRepresentation.QUEEN:
              board.setPiece(i, j, new Queen(i, j, isBlack));
              break;
            case PieceRepresentation.KING:
              board.setPiece(i, j, new King(i, j, isBlack));
              break;
          }
        }
      }
    }
  }

}
