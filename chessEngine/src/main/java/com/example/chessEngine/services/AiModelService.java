package com.example.chessEngine.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface AiModelService {
  void deleteConversation(String conversationId);
  List<Message> getConversation(String conversationId);
  ChatClient getChatClient();
}
