package com.example.ecommerce.dto;
import com.example.ecommerce.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
public record OrderStatusRequest(@NotNull OrderStatus status){}
