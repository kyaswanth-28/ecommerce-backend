package com.example.ecommerce.dto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
public record OrderResponse(Long id,String customerUsername,Long productId,String productName,int quantity,BigDecimal totalPrice,OrderStatus status,Instant createdAt){
 public static OrderResponse from(CustomerOrder o){return new OrderResponse(o.getId(),o.getCustomerUsername(),o.getProductId(),o.getProductName(),o.getQuantity(),o.getTotalPrice(),o.getStatus(),o.getCreatedAt());}
}
