package com.example.aiservice.service;

import com.example.aiservice.model.RecommendationItem;
import com.example.aiservice.repository.RecommendationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderQueryService {

    @Autowired
    private RecommendationItemRepository repository;

    public List<RecommendationItem> getAllOrders() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<RecommendationItem> getOrdersByUuid(String uuid) {
        return repository.findAllByUuid(uuid);
    }

    public Map<String, List<RecommendationItem>> getGroupedByPromptAndUuid(String uuid) {
        return repository.findAllByUuid(uuid).stream()
                .filter(item -> item.getPrompt() != null)
                .collect(Collectors.groupingBy(RecommendationItem::getPrompt));
    }

    public void deleteByPrompt(String uuid, String prompt) {
        repository.deleteAllByUuidAndPrompt(uuid, prompt);
    }
}
