package com.ai.SpringAIDemo.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private final String CREATE_RECIPE_TEMPLATE = "I want to create a recipe with the following ingredients: {ingredients}." +
            "Consider that you are not obligated to use all of these ingredients, but it is mandatory that you do not use any external ingredient." +
            "Also, the cuisine type I prefer is: {cuisine}." +
            "Consider and apply the following dietary restrictions, which are mandatory: {restrictions}" +
            "Please, provide me with a detailed recipe including Title, List of Ingredients and Cooking Instructions.";

    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String restrictions) {
        PromptTemplate promptTemplate = new PromptTemplate(this.CREATE_RECIPE_TEMPLATE);
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "restrictions", restrictions
        );
        Prompt prompt = promptTemplate.create(params);

        return this.chatModel.call(prompt).getResult().getOutput().getContent();
    }
}
