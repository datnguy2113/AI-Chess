package com.example.chessEngine.controller;


import com.example.chessEngine.ChessLogic.Board;
import com.example.chessEngine.ChessLogic.BoardInitializer;
import com.example.chessEngine.ChessLogic.PieceRepresentation;
import com.example.chessEngine.dto.*;
import com.example.chessEngine.services.BoardStateService;
import com.example.chessEngine.services.ChessAgentService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@CrossOrigin({"http://localhost:5173",
              "https://ai-chess-1df4.onrender.com"
})
public class GameController {

    private final BoardStateService boardStateService;
    private final ChessAgentService chessAgentService;

    @Autowired
    public GameController(BoardStateService boardStateService, ChessAgentService chessAgentService) {
        this.boardStateService = boardStateService;
        this.chessAgentService = chessAgentService;
    }

    @GetMapping("/game")
    public ResponseEntity<StateResponse> startGame() {
        String conversationId = UUID.randomUUID().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StateResponse
                        .builder()
                        .state(BoardInitializer.congig)
                        .conversationId(conversationId).
                        build());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<String[][]> getBoardState(@PathVariable  String id) {
        String[][] state = this.boardStateService.getBoardState(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(state);
    }


    @PostMapping("/game")
    public ResponseEntity<MoveResponse> getMove(@RequestBody MoveRequest request) {
        Board board = Board.parse(request.getState());
        Board clone = board.clone();
        int[] from = new int[2];
        int[] to = new int[2];
        while (true) {
          int[] move = this.chessAgentService.makeMove(request.getPlayerName(), request.isBlack(), board, request.getConversationId());
          from[0] = move[0];
          from[1] = move[1];
          to[0] = move[2];
          to[1] = move[3];
          boolean isValid = board.movePiece(from[0], from[1], to[0], to[1]);
          if (isValid) {
            break;
          }
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        MoveResponse.builder()
                                .from(from)
                                .to(to)
                                .move(PieceRepresentation.getMoveNotation(clone, from, to))
                                .isGameOver(board.isGameOver())
                                .state(board.getBoardString())
                                .build()
                );
    }

    @PostMapping("/game/make_move")
    public ResponseEntity<ValidateMoveResponse> validateMove(@RequestBody ValidateMoveRequest request) {
      Board board = Board.parse(request.getState());
      Board clone = board.clone();
      this.chessAgentService.isMoveValid(board, request.getFrom(), request.getTo());
      return ResponseEntity
          .status(HttpStatus.ACCEPTED)
          .body(
              ValidateMoveResponse
                  .builder()
                  .move(PieceRepresentation.getMoveNotation(clone, request.getFrom(), request.getTo()))
                  .state(board.getBoardString())
                  .isGameOver(board.isGameOver())
                  .build()
          );
    }

}
