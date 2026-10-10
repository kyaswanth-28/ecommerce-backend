 package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

 private final ProductService service;

 public ProductController(ProductService service) {
  this.service = service;
 }

 // Get all products with pagination, sorting and filtering
 @GetMapping
 public Page<ProductResponse> list(
         @RequestParam(name = "page", defaultValue = "0") int page,
         @RequestParam(name = "size", defaultValue = "10") int size,
         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
         @RequestParam(name = "direction", defaultValue = "asc") String direction,
         @RequestParam(name = "name", required = false) String name,
         @RequestParam(name = "category", required = false) String category) {

  return service.list(
          page,
          size,
          sortBy,
          direction,
          name,
          category
  );
 }

 // Get a single product by ID
 @GetMapping("/{id}")
 public ProductResponse get(@PathVariable("id") Long id) {
  return service.get(id);
 }

 // Create a product (admin only)
 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 @PreAuthorize("hasRole('ADMIN')")
 public ProductResponse create(
         @Valid @RequestBody ProductRequest request) {

  return service.create(request);
 }

 // Update a product (admin only)
 @PutMapping("/{id}")
 @PreAuthorize("hasRole('ADMIN')")
 public ProductResponse update(
         @PathVariable("id") Long id,
         @Valid @RequestBody ProductRequest request) {

  return service.update(id, request);
 }

 // Delete a product (admin only)
 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 @PreAuthorize("hasRole('ADMIN')")
 public void delete(@PathVariable("id") Long id) {
  service.delete(id);
 }
}
