package com.example.chessEngine.services;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAiModelServiceImpl implements AiModelService{
  private final ChatClient openAiChatClient;
  private final ChatMemory memory;
  private final int MAX_STORAGE = 100;

  public OpenAiModelServiceImpl(OpenAiChatModel openAiChatModel) {
    this.memory = MessageWindowChatMemory.builder().maxMessages(this.MAX_STORAGE).build();
    this.openAiChatClient = ChatClient.builder(openAiChatModel)
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(this.memory).build())
        .build();
  }

  @Override
  public void deleteConversation(String conversationId) {

  }

  @Override
  public ChatClient getChatClient() {
    return null;
  }
}
