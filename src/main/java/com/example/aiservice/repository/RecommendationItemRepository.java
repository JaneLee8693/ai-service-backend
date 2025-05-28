package com.example.aiservice.repository;

import com.example.aiservice.model.RecommendationItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendationItemRepository extends MongoRepository<RecommendationItem, String> {
    List<RecommendationItem> findAllByUuid(String uuid);
    List<RecommendationItem> findAllByUsernameAndUuid(String username, String uuid);
    List<RecommendationItem> findAllByUsernameAndUuidAndPrompt(String username, String uuid, String prompt);
    void deleteAllByUuidAndPrompt(String uuid, String prompt);
}
