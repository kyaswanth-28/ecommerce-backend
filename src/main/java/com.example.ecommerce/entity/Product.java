package com.example.ecommerce.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String name;
 @Column(length=2000) private String description;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
 @Column(nullable=false) private int stock;
 @Column(nullable=false,length=80) private String category;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @Column(nullable=false) private Instant updatedAt;
 @PrePersist void create(){createdAt=Instant.now();updatedAt=createdAt;}
 @PreUpdate void update(){updatedAt=Instant.now();}
 public Product(){}
 public Product(String n,String d,BigDecimal p,int s,String c){name=n;description=d;price=p;stock=s;category=c;}
 public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;}
 public BigDecimal getPrice(){return price;} public int getStock(){return stock;} public String getCategory(){return category;}
 public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
 public void setName(String v){name=v;} public void setDescription(String v){description=v;}
 public void setPrice(BigDecimal v){price=v;} public void setStock(int v){stock=v;} public void setCategory(String v){category=v;}
}
