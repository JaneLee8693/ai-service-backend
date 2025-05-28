package com.example.aiservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.aiservice.model.RecommendationItem;
import com.example.aiservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Order History", description = "View previously saved recommended orders")
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderQueryService orderQueryService;

    @Operation(
            summary = "Get saved recommendations",
            description = "Returns all recommendation items saved to MongoDB"
    )
    @ApiResponse(responseCode = "200", description = "Successfully fetched saved items")
    @GetMapping
    public List<RecommendationItem> getOrders(@RequestParam String uuid) {
        return orderQueryService.getOrdersByUuid(uuid);
    }

    @GetMapping("/grouped")
    public Map<String, List<RecommendationItem>> getGrouped(@RequestParam String uuid) {
        return orderQueryService.getGroupedByPromptAndUuid(uuid);
    }

    @DeleteMapping("/grouped/{prompt}")
    public void deleteGroup(@PathVariable String prompt, @RequestParam String uuid) {
        orderQueryService.deleteByPrompt(uuid, prompt);
    }

    @DeleteMapping("/{id}")
    public void deleteSingle(@PathVariable String id) {
        orderQueryService.deleteById(id);
    }
}
