package com.example.chessEngine.ChessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
  // Instance variables (add more if you need)
    private Piece[][] board;
    private final int ROWS = 8;
    private final int COLS = 8;

    public int getROWS() {
      return this.ROWS;
    }

    public int getCOLS() {
      return this.COLS;
    }

    public static Board parse(String[][] parse) {
        Board board = new Board();
        int nRows = 8;
        int nCols = 8;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                String piece = parse[i][j];
                if (piece != null) {
                    boolean isBlack = piece.charAt(0) == 'B';
                    String pieceType = piece.substring(1);
                    if (pieceType.equals(PieceRepresentation.PAWN)) {
                        board.setPiece(i, j, new Pawn(i, j, isBlack));
                    }
                    if (pieceType.equals(PieceRepresentation.BISHOP)) {
                        board.setPiece(i, j, new Bishop(i, j, isBlack));
                    }
                    if (pieceType.equals(PieceRepresentation.KNIGHT)) {
                        board.setPiece(i, j, new Knight(i, j, isBlack));
                    }
                    if (pieceType.equals(PieceRepresentation.KING)) {
                        board.setPiece(i, j, new King(i, j, isBlack));
                    }
                    if (pieceType.equals(PieceRepresentation.QUEEN)) {
                        board.setPiece(i, j, new Queen(i, j, isBlack));
                    }
                    if (pieceType.equals(PieceRepresentation.ROOK)) {
                        board.setPiece(i, j, new Rook(i, j, isBlack));
                    }
                }
            }
        }
        return board;

    }

    public String[][] getBoardString() {
      String[][] state = new String[this.ROWS][this.COLS];
      for (int i = 0; i < this.ROWS; i++) {
        for (int j = 0; j < this.COLS; j++) {
          Piece piece = this.getPiece(i, j);
          String pieceString;
          if (piece != null) {
            pieceString = PieceRepresentation.getPieceString(piece);
          }
          else {
            pieceString = null;
          }
          state[i][j] = pieceString;
        }
      }
      return state;
    }

    public String getFen() {
      String res = "";
      for (int i = 0; i < this.ROWS; i++) {
        for (int j = 0; j < this.COLS; j++) {
          Piece piece = this.getPiece(i, j);
          String pieceString;
          if (piece != null) {
            pieceString = PieceRepresentation.getPieceString(piece);
          }
          else {
            pieceString = "nu";
          }
          res += pieceString;
          if (j == this.COLS - 1) {
            res += "\n";
          }
          else {
            res += ",";
          }
        }
      }
      return res;
    }



  /**
   * Default Constructor
   */
  public Board() {
    // initialize the board to chessboard dimensions.
    board = new Piece[this.ROWS][this.COLS];
  }

  // Accessor Methods

  /**
   * Gets the piece at a particular row and column of the board.
   * @param row       The row of the piece to be accessed.
   * @param col       The column of the piece to be accessed.
   * @return          The piece at the specified row and column of the board.
   */
  public Piece getPiece(int row, int col) {
    return this.board[row][col];
  }

  /**
   * Sets the piece at a particular row and column of the board.
   * @param row       The row to place the piece at.
   * @param col       The column to place the piece at.
   * @param piece     The piece to place at the specified row and column.
   */
  public void setPiece(int row, int col, Piece piece) {
    this.board[row][col] = piece;
  }

  // Movement helper functions

  /**
   * Verifies that the source and destination of a move are valid by performing the following checks:
   *  1. ALL rows and columns provided must be >= 0.
   *  2. ALL rows and columns provided must be < 8.
   *  3. The start position of the move must contain a piece.
   *  4. The piece at the starting position must be the correct color.
   *  5. The destination must be empty OR must contain a piece of the opposite color.
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @param isBlack   The expected color of the starting piece.
   * @return True if the above conditions are met, false otherwise.
   */
  public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
    if (startRow < 0 || startRow >= 8) {
      return false;
    }
    if (startCol < 0 || startCol >= 8) {
      return false;
    }
    if (endRow < 0 || endRow >= 8) {
      return false;
    }
    if (endCol < 0 || endCol >= 8) {
      return false;
    }
    if (this.board[startRow][startCol] == null) {
      return false;
    }
    if (this.board[startRow][startCol].isBlack != isBlack) {
      return false;
    }
    if (this.board[endRow][endCol] != null && this.board[endRow][endCol].isBlack == isBlack) {
      return false;
    }
    return true;
  }

  /**
   * Verifies that the source and destination of a move are adjacent squares (within 1 square of each other)
   * Example, Piece P is adjacent to the spots marked X:
   * OOOOO
   * OXXXO
   * OXPXO
   * OXXXO
   * OOOOO
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @return True if the source and destination squares are adjacent, false otherwise.
   */
  public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
    int dx = Math.abs(startCol - endCol);
    int dy = Math.abs(startRow - endRow);
    return dx >= 0 && dx <= 1 && dy >= 0 && dy <= 1;
  }

  /**
   * Verifies that a source and destination are in the same row and that there are no pieces on squares
   * between the source and the destination.
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @return True if source and destination are in same row with no pieces between them, false otherwise.
   */
  public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
    if (startRow != endRow) {
      return false;
    }

    int step = endCol > startCol ? 1 : -1;
    int col = startCol + step;
    while (col != endCol) {
      if (this.getPiece(startRow, col) != null) {
        return false;
      }
      col += step;
    }
    return true;
  }

  /**
   * Verifies that a source and destination are in the same column and that there are no pieces on squares
   * between the source and the destination.
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @return True if source and destination are in same column with no pieces between them, false otherwise.
   */
  public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
    if (startCol != endCol) {
      return false;
    }

    int step = endRow > startRow ? 1 : -1;
    int row = startRow + step;
    while (row != endRow) {
      if (this.getPiece(row, startCol) != null) {
        return false;
      }
      row += step;
    }

    return true;
  }

  /**
   * Verifies that a source and destination are on the same diagonal and that there are no pieces on squares
   * between the source and the destination.
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @return True if source and destination are on the same diagonal with no pieces between them, false otherwise.
   */
  public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
    int dx = Math.abs(endCol - startCol);
    int dy = Math.abs(endRow - startRow);

    if (dx != dy) {
      return false;
    }
    int horizontalStep = endCol > startCol ? 1 : -1;
    int verticalStep = endRow > startRow ? 1 : -1;

    int row = startRow + verticalStep;
    int col = startCol + horizontalStep;

    while (row != endRow && col != endCol) {
      if (this.getPiece(row, col) != null) {
        return false;
      }
      row += verticalStep;
      col += horizontalStep;
    }

    return true;
  }

  // Game functionality methods

  /**
   * Moves the piece from startRow, startCol to endRow, endCol if it is legal to do so.
   * IMPORTANT: Make sure to update the internal position of the piece, and the starting position of the piece to null!
   * @param startRow  The starting row of the move.
   * @param startCol  The starting column of the move.
   * @param endRow    The ending row of the move.
   * @param endCol    The ending column of the move.
   * @return Whether the move was successfully completed or not. (Moves are not completed if they are not legal.)
   */
  public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
    Piece startPiece = this.board[startRow][startCol];
    if (startPiece == null) {
      return false;
    }
    if (!verifySourceAndDestination(startRow, startCol, endRow, endCol, startPiece.isBlack)) {
      return false;
    }
    if (!startPiece.isMoveLegal(this, endRow, endCol)) {
      return false;
    }
    this.setPiece(startRow, startCol, null);
    this.setPiece(endRow, endCol, startPiece);
    startPiece.setPosition(endRow, endCol);
    return true;
  }

  /**
   * Returns true if there are fewer than TWO kings on the board.
   * @return If the game is in a game over state.
   */
  public boolean isGameOver() {
    int numKing = 0;
    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        Piece piece = this.getPiece(i, j);
        if (piece != null &&
            ( piece.representation == PieceRepresentation.WHITE_KING_CODE ||
                piece.representation == PieceRepresentation.BLACK_KING_CODE)) {
          numKing++;
        }
      }
    }

    return numKing < 2;
  }

  /**
   * Sets all indexes in the board to null
   */
  public void clear() {
    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        this.board[i][j] = null;
      }
    }
  }


  public Map<String, List<int[]>> getLegalMoves(boolean isBlack) {
    List<Piece> pieces = new ArrayList<>();

    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        Piece piece = this.getPiece(i, j);
        if (piece != null && piece.isBlack == isBlack) {
          pieces.add(piece);
        }
      }
    }


    Map<String, List<int[]>> movesMap = new HashMap<>();
    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        for (Piece piece: pieces) {
          if (piece.isMoveLegal(this, i, j)) {
            String piecePosition = piece.getStringPosition();
            if (!movesMap.containsKey(piecePosition)) {
              movesMap.put(piecePosition, new ArrayList<>());
            }
            movesMap.get(piecePosition).add(new int[] {i, j});
          }
        }
      }
    }
    return movesMap;
  }

  public String getStringLegalMoves(boolean isBlack) {
    Map<String, List<int[]>> legalMoves = this.getLegalMoves(isBlack);
    String res = "";
    for (String from: legalMoves.keySet()) {
      String[] froms = from.split(" ");
      String fromRow = froms[0];
      String fromCol = froms[1];
      List<int[]> tos = legalMoves.get(from);
      for (int[] to: tos) {
        res += fromRow + "," + fromCol + "," + to[0] + "," + to[1] + "\n";
      }
    }
    return res;
  }



  public Character[][] getState() {
    Character[][] state = new Character[this.ROWS][this.COLS];

    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        Piece piece = this.getPiece(i, j);
        if (piece != null) {
          state[i][j] = piece.representation;
        }
      }
    }
    return  state;
  }

  public Board clone() {
    Board newBoard = new Board();
    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        Piece piece = this.getPiece(i, j);
        if (piece != null) {
          newBoard.setPiece(i, j, piece.clone());
        }
      }
    }
    return newBoard;
  }

  public List<Map<String, Integer>> countPiece() {
    List<Map<String, Integer>> counts = new ArrayList<>();
    Map<String, Integer> blackCounts = new HashMap<>();
    Map<String, Integer> whiteCounts = new HashMap<>();
    for (int i = 0; i < this.ROWS; i++) {
      for (int j = 0; j < this.COLS; j++) {
        Piece piece = this.getPiece(i, j);
        if (piece == null) {
          continue;
        }
        Map<String, Integer> count = piece.isBlack ? blackCounts : whiteCounts;
        if (!count.containsKey(piece.type)) {
            count.put(piece.type, 0);
        }
        count.put(piece.type, count.get(piece.type) + 1);
      }
    }
    counts.add(blackCounts);
    counts.add(whiteCounts);
    return counts;
  }


  public void display() {
    System.out.println(this);
  }


  public String toString() {
    StringBuilder out = new StringBuilder();
    out.append(" ");
    for(int i = 0; i < 8; i++){
      out.append(" ");
      out.append(i);
    }
    out.append('\n');
    for(int i = 0; i < board.length; i++) {
      out.append(i);
      out.append("|");
      for(int j = 0; j < board[0].length; j++) {
        out.append(board[i][j] == null ? "\u2001|" : board[i][j] + "|");
      }
      out.append("\n");
    }
    return out.toString();
  }
}