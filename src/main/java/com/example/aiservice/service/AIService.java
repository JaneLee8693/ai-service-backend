package com.example.aiservice.service;

import com.example.aiservice.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public Map<String, Object> getRecommendations(String prompt, String username) {
        try {
            // Craft the prompt to send to OpenAI
            String fullPrompt = "The user provided the following input: \"" + prompt + "\". " +
                    "Based only on food and drink items, recommend up to three order items. " +
                    "Each item should include: item (product name), quantity (number), and notes (short description). " +
                    "Do not include non-food or non-drink items. Respond in pure JSON array format. No Markdown or explanation.";

            // Build request payload
            String requestBodyJson = objectMapper.writeValueAsString(Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are an AI assistant for product recommendation."),
                            Map.of("role", "user", "content", fullPrompt)
                    )
            ));

            RequestBody body = RequestBody.create(
                    requestBodyJson,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(OPENAI_API_URL)
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .post(body)
                    .build();

            // Send request to OpenAI
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return Map.of(
                            "status", "error",
                            "message", "OpenAI API returned error: " + response.code()
                    );
                }

                // Parse the response
                JsonNode root = objectMapper.readTree(response.body().string());
                String content = root.path("choices").get(0).path("message").path("content").asText();

                // Clean up JSON formatting
                content = content.replaceAll("(?s)```json|```", "").trim();
                JsonNode recommendations = objectMapper.readTree(content);

                // Wrap with prompt and username
                ObjectNode wrapper = objectMapper.createObjectNode();
                wrapper.put("prompt", prompt);
                wrapper.put("username", username);
                wrapper.set("items", recommendations);

                // Send to Kafka
                kafkaProducerService.sendMessage(wrapper.toString());

                // Return response to frontend
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
