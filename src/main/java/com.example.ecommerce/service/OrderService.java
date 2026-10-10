package com.example.ecommerce.service;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderService {
 private final CustomerOrderRepository orders; private final ProductRepository products;
 public OrderService(CustomerOrderRepository o,ProductRepository p){orders=o;products=p;}
 @Transactional @CacheEvict(cacheNames="products", allEntries=true) public OrderResponse place(String username,CreateOrderRequest r){
  Product p=products.findById(r.productId()).orElseThrow(()->new ResourceNotFoundException("Product not found: "+r.productId()));
  if(p.getStock()<r.quantity())throw new IllegalArgumentException("Insufficient stock");
  p.setStock(p.getStock()-r.quantity());products.save(p);
  BigDecimal total=p.getPrice().multiply(BigDecimal.valueOf(r.quantity()));
  return OrderResponse.from(orders.save(new CustomerOrder(username,p.getId(),p.getName(),r.quantity(),total)));
 }
 @Transactional(readOnly=true) public List<OrderResponse> mine(String u){return orders.findByCustomerUsernameOrderByCreatedAtDesc(u).stream().map(OrderResponse::from).toList();}
 @Transactional(readOnly=true) public List<OrderResponse> all(){return orders.findAllByOrderByCreatedAtDesc().stream().map(OrderResponse::from).toList();}
 @Transactional public OrderResponse status(Long id,OrderStatus s){CustomerOrder o=orders.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found: "+id));o.setStatus(s);return OrderResponse.from(orders.save(o));}
}
