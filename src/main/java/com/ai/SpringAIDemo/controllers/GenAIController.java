package com.ai.SpringAIDemo.controllers;

import com.ai.SpringAIDemo.services.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenAIController {
    private ChatService chatService;

    public GenAIController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("ask-ai")
    public String getResponse(String prompt) {
        return this.chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(String prompt) {
        return this.chatService.getResponseOptions(prompt);
    }
}
