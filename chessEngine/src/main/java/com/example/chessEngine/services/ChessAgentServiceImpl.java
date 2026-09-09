package com.example.chessEngine.services;

import com.example.chessEngine.Agent.*;
import com.example.chessEngine.ChessLogic.Board;
import com.example.chessEngine.exception.AgentNotFoundException;
import com.example.chessEngine.exception.IllegalMoveException;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChessAgentServiceImpl implements ChessAgentService{

    private final ChatClient openAiChatClient;
    private final ChatClient claudeChatClient;
    private final Map<String, Agent> blackAgents = new HashMap<>();
    private final Map<String, Agent> whiteAgents = new HashMap<>();


    public ChessAgentServiceImpl(OpenAiChatModel openAiChatModel, AnthropicChatModel anthropicChatModel) {
      ChatMemory openAiChatMemory = MessageWindowChatMemory.builder().maxMessages(100).build();
      this.openAiChatClient = ChatClient.builder(openAiChatModel)
          .defaultAdvisors(MessageChatMemoryAdvisor.builder(openAiChatMemory).build())
          .build();
      this.claudeChatClient = ChatClient.create(anthropicChatModel);

      this.blackAgents.put(AgentName.RANDOM, new RandomAgent(AgentName.RANDOM, true));
      this.blackAgents.put(AgentName.CHATGPT, new ChatGptAgent(AgentName.CHATGPT, openAiChatClient, true));
      this.blackAgents.put(AgentName.CLAUDE, new ClaudeAgent(AgentName.CLAUDE, claudeChatClient,true));
      this.blackAgents.put(AgentName.ALPHA_BETA, new AlphaBetaAgent(AgentName.ALPHA_BETA, true));

      this.whiteAgents.put(AgentName.RANDOM, new RandomAgent(AgentName.RANDOM, false));
      this.whiteAgents.put(AgentName.CHATGPT, new ChatGptAgent(AgentName.CHATGPT, openAiChatClient, false));
      this.whiteAgents.put(AgentName.CLAUDE, new ClaudeAgent(AgentName.CLAUDE, claudeChatClient,false));
      this.whiteAgents.put(AgentName.ALPHA_BETA, new AlphaBetaAgent(AgentName.ALPHA_BETA, false));

    }

    @Override
    public int[] makeMove(String agentName, boolean isBlack, Board state, String conversationId) {
        Map<String, Agent> agents = isBlack ? this.blackAgents : this.whiteAgents;
        Agent agent = agents.get(agentName);
        if (agent == null) {
            throw new AgentNotFoundException();
        }
        int[] move = agent.getName().equals(AgentName.CHATGPT) ?  agent.makeMove(state, conversationId) : agent.makeMove(state);
        return move;
    }

    @Override
    public boolean isMoveValid(Board board, int[] from, int[] to) {
      boolean isValid = board.movePiece(from[0], from[1], to[0], to[1]);
      if (!isValid) {
        throw new IllegalMoveException();
      }
      return isValid;
    }


}
