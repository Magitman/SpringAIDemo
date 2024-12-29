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

    public List<String> generateImageOptions(String prompt) {
        ImageResponse imageResponse = this.imageModel.call(new ImagePrompt(prompt,
                OpenAiImageOptions.builder()
                        .withModel("dall-e-2")
                        .withQuality("hd")
                        .withN(3)
                        .withHeight(1024)
                        .withWidth(1024).build()));

        List<String> imagesGenerated = imageResponse.getResults().stream().map(
                imageGeneration -> imageGeneration.getOutput().getUrl()
        ).toList();

        return imagesGenerated;
    }
}
