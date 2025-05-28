package com.example.aiservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.aiservice.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "AI Recommendations", description = "Generate order recommendations from natural language")
@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    @Autowired
    private AIService aiService;


    @Operation(
            summary = "Generate AI-based recommendations",
            description = "Takes in a natural language prompt and returns 1-3 suggested order items."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recommendations generated successfully"),
            @ApiResponse(responseCode = "500", description = "AI service failure or OpenAI unreachable")
    })
    @PostMapping("/recommend")
    public Map<String, Object> recommend(@RequestBody Map<String, String> input) {
        String prompt = input.get("prompt");
        String username = input.get("username");
        String uuid = input.get("uuid");
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt is required");
        }
        return aiService.getRecommendations(prompt, username, uuid);
    }
}
