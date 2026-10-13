package com.example.chessEngine.services;

import org.springframework.ai.chat.client.ChatClient;

public interface AiModelService {
  void deleteConversation(String conversationId);
  ChatClient getChatClient();
}
