package com.example.aiservice.repository;

import com.example.aiservice.model.RecommendationItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationItemRepository extends MongoRepository<RecommendationItem, String> {
}
