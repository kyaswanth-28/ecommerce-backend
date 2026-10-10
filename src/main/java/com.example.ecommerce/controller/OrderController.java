package com.example.ecommerce.controller;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderService service;
 public OrderController(OrderService s){service=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
 public OrderResponse place(@Valid @RequestBody CreateOrderRequest r,Authentication a){return service.place(a.getName(),r);}
 @GetMapping("/my") @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
 public List<OrderResponse> mine(Authentication a){return service.mine(a.getName());}
 @GetMapping @PreAuthorize("hasRole('ADMIN')") public List<OrderResponse> all(){return service.all();}
 @PatchMapping("/{id}/status") @PreAuthorize("hasRole('ADMIN')")
 public OrderResponse status(@PathVariable Long id,@Valid @RequestBody OrderStatusRequest r){return service.status(id,r.status());}
}
