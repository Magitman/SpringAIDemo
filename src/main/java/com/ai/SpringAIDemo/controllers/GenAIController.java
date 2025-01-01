package com.ai.SpringAIDemo.controllers;

import com.ai.SpringAIDemo.exceptions.RecipeNotFoundException;
import com.ai.SpringAIDemo.services.ChatService;
import com.ai.SpringAIDemo.services.ImageService;
import com.ai.SpringAIDemo.services.RecipeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("gen-ai")
@Validated
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
    public String getResponse(@RequestParam @NotNull String prompt) {
        return this.chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam @NotNull String prompt) {
        return this.chatService.getResponseOptions(prompt);
    }

    @GetMapping("generate-image")
    public String generateImage(@RequestParam @NotNull String prompt) {
        String image = "<img src=\"" + this.imageService.generateImage(prompt) + "\" alt=\"" + prompt + "\">";
        return image;
    }

    @GetMapping("generate-image-options")
    public String generateImageOptions(@RequestParam @NotNull String prompt,
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
    public String generateRecipe(@RequestParam @NotNull String ingredients, @RequestParam(defaultValue = "Any") String cuisine, @RequestParam(defaultValue = "None") String restrictions) throws RecipeNotFoundException {
        return this.recipeService.createRecipe(ingredients, cuisine, restrictions);
    }
}
