package com.example.ecommerce.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity @Table(name="customer_orders")
public class CustomerOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String customerUsername;
 @Column(nullable=false) private Long productId;
 @Column(nullable=false,length=120) private String productName;
 @Column(nullable=false) private int quantity;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal totalPrice;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private OrderStatus status=OrderStatus.PENDING;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void onCreate(){createdAt=Instant.now();}
 public CustomerOrder(){}
 public CustomerOrder(String u,Long p,String n,int q,BigDecimal t){customerUsername=u;productId=p;productName=n;quantity=q;totalPrice=t;}
 public Long getId(){return id;} public String getCustomerUsername(){return customerUsername;} public Long getProductId(){return productId;}
 public String getProductName(){return productName;} public int getQuantity(){return quantity;} public BigDecimal getTotalPrice(){return totalPrice;}
 public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus s){status=s;} public Instant getCreatedAt(){return createdAt;}
}
