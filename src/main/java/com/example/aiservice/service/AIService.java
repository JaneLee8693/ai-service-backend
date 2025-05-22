package com.example.aiservice.service;

import com.example.aiservice.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service
public class AIService {
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Map<String, Object> getRecommendations(String userInput) {
        try {
            String prompt = "The user provided the following input: \"" + userInput + "\". " +
                    "Based on the user's intent, recommend up to three order items. " +
                    "Each item should include: item (product name), quantity (number), and notes (short description). " +
                    "Respond in a pure JSON array format. Do not include Markdown or explanation.";

            String requestBodyJson = objectMapper.writeValueAsString(Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                    Map.of("role", "system", "content", "You are an AI assistant for product recommendation."),
                    Map.of("role", "user", "content", prompt)
                )
            ));

            RequestBody body = RequestBody.create(
                requestBodyJson, MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(OPENAI_API_URL)
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return Map.of(
                            "status", "error",
                            "message", "OpenAI API returned error: " + response.code()
                    );
                }
                JsonNode root = objectMapper.readTree(response.body().string());
                String content = root.path("choices").get(0).path("message").path("content").asText();
                // remove ```json ``` or ```
                content = content.replaceAll("(?s)```json|```", "").trim();
                JsonNode recommendations = objectMapper.readTree(content);
                // Send recommendations to Kafka
                kafkaProducerService.sendMessage(recommendations.toString());
                return Map.of(
                        "status", "success",
                        "data", recommendations
                );
            }
        } catch (IOException e) {
            return Map.of(
                    "status", "error",
                    "message", "Parsing failed: " + e.getMessage()
            );
        }
    }
}
