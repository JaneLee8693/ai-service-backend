package com.example.aiservice.service;

import com.example.aiservice.model.RecommendationItem;
import com.example.aiservice.repository.RecommendationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    private RecommendationItemRepository repository;

    public List<RecommendationItem> getAllOrders() {
        return repository.findAll();
    }
}
