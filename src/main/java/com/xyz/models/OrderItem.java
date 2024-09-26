package com.xyz.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private UseerOrd useerOrd;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private String size;
    
    private int quantity;
    
    private Integer price;
    
    private Integer discountedPrice;
    
    // Consider removing or documenting this field if it's necessary
    private Long userId;
    
    private LocalDateTime deliveryDateTime;
}