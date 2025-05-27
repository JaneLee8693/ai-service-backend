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

    public List<RecommendationItem> getAllOrders(String username) {
        return repository.findAllByUsername(username);
    }

    public Map<String, List<RecommendationItem>> getGroupedByPrompt(String username) {
        List<RecommendationItem> list = repository.findAllByUsername(username);
        return list.stream()
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
