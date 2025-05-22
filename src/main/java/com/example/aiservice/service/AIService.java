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
            String prompt = "使用者輸入了一段文字：「" + userInput + "」。請根據此語意推薦最多三個訂單項目，" +
                    "每項包含：item（商品名稱）、quantity（數量）、notes（簡短備註），並以 JSON 陣列格式輸出。";

            String requestBodyJson = objectMapper.writeValueAsString(Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                    Map.of("role", "system", "content", "你是一個語意推薦助手"),
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
                    return Map.of("error", "OpenAI API 回應錯誤: " + response.code());
                }
                JsonNode root = objectMapper.readTree(response.body().string());
                String content = root.path("choices").get(0).path("message").path("content").asText();
                // remove ```json ``` or ```
                content = content.replaceAll("(?s)```json|```", "").trim();
                JsonNode recommendations = objectMapper.readTree(content);
                // Send recommendations to Kafka
                kafkaProducerService.sendMessage(recommendations.toString());
                return Map.of("recommendations", recommendations);
            }
        } catch (IOException e) {
            return Map.of("error", "解析失敗: " + e.getMessage());
        }
    }
}
