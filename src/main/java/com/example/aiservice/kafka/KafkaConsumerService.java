package com.example.aiservice.kafka;

import com.example.aiservice.model.RecommendationItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service
public class KafkaConsumerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "recommendations", groupId = "recommendation-consumer-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String json = record.value();
            JsonNode root = objectMapper.readTree(json);

            String prompt = root.path("prompt").asText();
            String username = root.path("username").asText();
            String uuid = root.path("uuid").asText();

            JsonNode itemsNode = root.path("items");

            List<RecommendationItem> items = objectMapper.convertValue(
                    itemsNode, new TypeReference<>() {}
            );

            for (RecommendationItem item : items) {
                item.setPrompt(prompt);
                item.setUsername(username);
                item.setUuid(uuid);
                mongoTemplate.save(item);
            }

            System.out.println("✅ Saved to MongoDB: " + items.size() +
                    " items with prompt=" + prompt + ", user=" + username + ", uuid=" + uuid);

        } catch (Exception e) {
            System.err.println("❌ MongoDB Save Failed: " + e.getMessage());
        }
    }
}
