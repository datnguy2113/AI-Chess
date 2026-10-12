package com.example.chessEngine.Agent;

import com.example.chessEngine.ChessLogic.Board;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;

public class ChatGptAgent extends Agent{
  private final ChatClient chatGptChatClient;

  public ChatGptAgent(String name, ChatClient chatGptChatClient, boolean isMyPieceBlack) {
    super.name = name;
    super.myPieceIsBlack = isMyPieceBlack;
    this.chatGptChatClient = chatGptChatClient;
  }

  @Override
  public int[] makeMove(Board board, String conversationId) {
    String template = com.example.chessEngine.Agent.Prompt.text;
    PromptTemplate promptTemplate = new PromptTemplate(template);
    Prompt prompt = promptTemplate.create(Map.of("board", board.getFen(),
        "side", this.isMyPieceIsBlack() ? "Black" : "White",
        "legalMoves", board.getStringLegalMoves(this.myPieceIsBlack)
    ));
    while (true) {
      try {
        String response = this.chatGptChatClient
            .prompt(prompt)
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
            .call().content();
        String[] moves = response.split(",");
        return new int[] {
            Integer.parseInt(moves[0]),
            Integer.parseInt(moves[1]),
            Integer.parseInt(moves[2]),
            Integer.parseInt(moves[3])
        };
      }
      catch (Exception exception) {

      }
    }
  }

  @Override
  public int[] makeMove(Board board) {
    String template = com.example.chessEngine.Agent.Prompt.text;
    PromptTemplate promptTemplate = new PromptTemplate(template);
    Prompt prompt = promptTemplate.create(Map.of("board", board.getFen(),
                                                  "side", this.isMyPieceIsBlack() ? "Black" : "White",
                                                    "legalMoves", board.getStringLegalMoves(this.myPieceIsBlack)
    ));
    while (true) {
      try {
        String response = this.chatGptChatClient
            .prompt(prompt)
            .call().content();
        String[] moves = response.split(",");
        return new int[] {
            Integer.parseInt(moves[0]),
            Integer.parseInt(moves[1]),
            Integer.parseInt(moves[2]),
            Integer.parseInt(moves[3])
        };
      }
      catch (Exception exception) {

      }
    }
  }
}
