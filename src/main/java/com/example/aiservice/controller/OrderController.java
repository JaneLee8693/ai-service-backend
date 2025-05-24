package com.example.aiservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.aiservice.model.RecommendationItem;
import com.example.aiservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order History", description = "View previously saved recommended orders")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderQueryService orderQueryService;

    @Operation(
            summary = "Get saved recommendations",
            description = "Returns all recommendation items saved to MongoDB"
    )
    @ApiResponse(responseCode = "200", description = "Successfully fetched saved items")
    @GetMapping
    public List<RecommendationItem> getOrders() {
        return orderQueryService.getAllOrders();
    }
}
