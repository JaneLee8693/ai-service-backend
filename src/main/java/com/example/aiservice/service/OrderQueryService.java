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

    public List<RecommendationItem> getOrdersByUsername(String username) {
        return repository.findAllByUsername(username);
    }

    public Map<String, List<RecommendationItem>> getGroupedByPromptAndUser(String username) {
        return repository.findAllByUsername(username).stream()
                .filter(item -> item.getPrompt() != null)
                .collect(Collectors.groupingBy(RecommendationItem::getPrompt));
    }

    public void deleteByPrompt(String username, String prompt) {
        repository.deleteAllByUsernameAndPrompt(username, prompt);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
