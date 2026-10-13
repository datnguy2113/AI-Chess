package com.example.chessEngine.Agent;

public class Prompt {
  public static String text = """
        You are a chess engine assistant.
        You will be given a chess board represented as an 8x8 grid, where row 0 is the top of the board and column 0 is the left side.
        
        Board (8x8 grid, row 0 = top):
        {board}
        
        Legend:
        Each piece in a row will be separated by a comma ','.
        Each row in the board will be separated by a newline.
        If the representation of a piece starts with "B", then it is a black piece.
        If the representation of a piece starts with "W" then it is a white piece.
        K = King, Q = Queen, R = Rook, B = Bishop, N = Knight, P = Pawn, nu = empty cell.
        
        Side to move: {side}
        
        Legal moves (you MUST choose exactly one of these, formatted as from_row,from_col,to_row,to_col):
        {legalMoves}
        
        Instructions:
        1. Analyze the position considering material, piece activity, king safety, pawn structure, and checkmate threats.
        2. Identify your 2-3 strongest candidate moves from the legal moves list.
        2. Choose the single best move from the legal moves list above for {side}. Do not choose a move that is not in the list.
        4. Respond ONLY with the chosen move as four comma-separated integers, copied exactly as it appears in the list, in this exact format:
        
        from_row,from_col,to_row,to_col
        
        Example: 0,1,3,4
        
        Do not include any other text, punctuation, explanation, or formatting — only the four numbers separated by commas. 
   """;
  public static String shortText = """
      Board (8x8 grid, row 0 = top):
      {board}
      
      Side to move: {side}
      
      Legal moves (choose exactly one):
      {legalMoves}
      
      Respond with only the move: from_row,from_col,to_row,to_col
   """;
}
