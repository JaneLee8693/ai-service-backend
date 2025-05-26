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

    public Map<String, List<RecommendationItem>> getOrdersGroupedByPrompt() {
        List<RecommendationItem> all = repository.findAll();
        return all.stream()
                .filter(item -> item.getPrompt() != null)
                .collect(Collectors.groupingBy(RecommendationItem::getPrompt));
    }

    public void deleteByPrompt(String prompt) {
        repository.deleteAllByPrompt(prompt);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
