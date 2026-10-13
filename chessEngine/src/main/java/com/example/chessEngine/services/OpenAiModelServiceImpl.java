package com.example.chessEngine.services;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAiModelServiceImpl implements AiModelService{


  @Override
  public void deleteConversation(String conversationId) {

  }

  @Override
  public ChatClient getChatClient() {
    return null;
  }
}
