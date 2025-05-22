package com.example.aiservice.controller;

import com.example.aiservice.service.AIService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/recommend")
    public Map<String, Object> recommend(@RequestBody Map<String, String> input) {
        return aiService.getRecommendations(input.get("text"));
    }
}
