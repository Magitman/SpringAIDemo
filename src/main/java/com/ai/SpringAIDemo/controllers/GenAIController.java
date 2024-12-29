package com.ai.SpringAIDemo.controllers;

import com.ai.SpringAIDemo.services.ChatService;
import com.ai.SpringAIDemo.services.ImageService;
import com.ai.SpringAIDemo.services.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenAIController {
    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("ask-ai")
    public String getResponse(String prompt) {
        return this.chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(String prompt) {
        return this.chatService.getResponseOptions(prompt);
    }

    @GetMapping("generate-image")
    public String generateImage(String prompt) {
        String image = "<img src=\"" + this.imageService.generateImage(prompt) + "\" alt=\"" + prompt + "\">";
        return image;
    }

    @GetMapping("generate-image-options")
    public String generateImageOptions(String prompt,
                                       @RequestParam(defaultValue = "dall-e-3") String model,
                                       @RequestParam(defaultValue = "hd") String quality,
                                       @RequestParam(defaultValue = "1") int n,
                                       @RequestParam(defaultValue = "1024") int width,
                                       @RequestParam(defaultValue = "1024") int height) {
        StringBuilder imageUrls = new StringBuilder();
        List<String> images = this.imageService.generateImageOptions(prompt, model, quality, n, width, height);

        for (String imageUrl : images) {
            imageUrls.append("<img src=\"").append(imageUrl).append("\" alt=\"").append(prompt).append("\">\n");
        }
        return imageUrls.toString();
    }

    @GetMapping("generate-recipe")
    public String generateRecipe(String ingredients, @RequestParam(defaultValue = "Any") String cuisine, @RequestParam(defaultValue = "None") String restrictions) {
        return this.recipeService.createRecipe(ingredients, cuisine, restrictions);
    }
}
