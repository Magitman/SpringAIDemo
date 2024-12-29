package com.ai.SpringAIDemo.services;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final OpenAiImageModel imageModel;

    public ImageService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public String generateImage(String prompt) {
        ImageResponse imageResponse = this.imageModel.call(new ImagePrompt(prompt));
        return imageResponse.getResult().getOutput().getUrl();
    }

    public List<String> generateImageOptions(String prompt, String model, String quality, int n, int width, int height) {
        ImageResponse imageResponse = this.imageModel.call(new ImagePrompt(prompt,
                OpenAiImageOptions.builder()
                        .withModel(model)
                        .withQuality(quality)
                        .withN(n)
                        .withHeight(height)
                        .withWidth(width).build()));

        List<String> imagesGenerated = imageResponse.getResults().stream().map(
                imageGeneration -> imageGeneration.getOutput().getUrl()
        ).toList();

        return imagesGenerated;
    }
}
