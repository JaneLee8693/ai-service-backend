package com.example.aiservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "recommendation_items")
public class RecommendationItem {

    @Id
    private String id;

    @NotBlank(message = "Item name must not be blank")
    private String item;
    private int quantity;
    private String notes;

    @NotBlank(message = "Prompt is required")
    private String prompt;

    @NotBlank(message = "Username is required")
    private String username;

    public RecommendationItem() {}

    public RecommendationItem(String item, int quantity, String notes, String prompt, String username) {
        this.item = item;
        this.quantity = quantity;
        this.notes = notes;
        this.prompt = prompt;
        this.username = username;
    }

    // Getter / Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
