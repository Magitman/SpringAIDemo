package com.ai.SpringAIDemo.services;

import org.springframework.ai.chat.model.ChatModel;

public class ChatService {
    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt) {
        return this.chatModel.call(prompt);
    }
}
