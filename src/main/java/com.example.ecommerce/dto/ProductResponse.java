package com.example.ecommerce.dto;
import com.example.ecommerce.entity.Product;
import java.math.BigDecimal;
import java.time.Instant;
public record ProductResponse(Long id,String name,String description,BigDecimal price,int stock,String category,Instant createdAt,Instant updatedAt){
 public static ProductResponse from(Product p){return new ProductResponse(p.getId(),p.getName(),p.getDescription(),p.getPrice(),p.getStock(),p.getCategory(),p.getCreatedAt(),p.getUpdatedAt());}
}
