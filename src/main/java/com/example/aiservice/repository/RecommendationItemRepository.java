package com.example.aiservice.repository;

import com.example.aiservice.model.RecommendationItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendationItemRepository extends MongoRepository<RecommendationItem, String> {
    List<RecommendationItem> findAllByUsername(String username);
    List<RecommendationItem> findAllByUsernameAndPrompt(String username, String prompt);
    void deleteAllByUsernameAndPrompt(String username, String prompt);
}
