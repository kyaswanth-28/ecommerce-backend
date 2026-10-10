 package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.ProductRepository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

 private final ProductRepository repo;

 public ProductService(ProductRepository repo) {
  this.repo = repo;
 }

 // Get products with pagination, sorting and filtering
 @Transactional(readOnly = true)
 public Page<ProductResponse> list(
         int page,
         int size,
         String sortBy,
         String dir,
         String name,
         String category) {

  if (page < 0) {
   throw new IllegalArgumentException(
           "Page must be zero or greater");
  }

  if (size < 1 || size > 100) {
   throw new IllegalArgumentException(
           "Size must be between 1 and 100");
  }

  if (!Set.of(
          "id", "name", "price", "stock",
          "category", "createdAt"
  ).contains(sortBy)) {
   throw new IllegalArgumentException(
           "Unsupported sortBy field");
  }

  Sort.Direction direction;

  try {
   direction = Sort.Direction.fromString(dir);
  } catch (Exception e) {
   throw new IllegalArgumentException(
           "Direction must be asc or desc");
  }

  Pageable pageable = PageRequest.of(
          page,
          size,
          Sort.by(direction, sortBy)
  );

  boolean hasName = name != null && !name.isBlank();
  boolean hasCategory =
          category != null && !category.isBlank();

  Page<Product> result;

  if (hasName && hasCategory) {
   result = repo.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(
           name.trim(), category.trim(), pageable);
  } else if (hasName) {
   result = repo.findByNameContainingIgnoreCase(
           name.trim(), pageable);
  } else if (hasCategory) {
   result = repo.findByCategoryIgnoreCase(
           category.trim(), pageable);
  } else {
   result = repo.findAll(pageable);
  }

  return result.map(ProductResponse::from);
 }

 // Get one product by ID
 @Transactional(readOnly = true)
 public ProductResponse get(Long id) {
  return repo.findById(id)
          .map(ProductResponse::from)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Product not found: " + id));
 }

 // Create a product
 public ProductResponse create(ProductRequest request) {
  Product product = new Product(
          request.name().trim(),
          request.description(),
          request.price(),
          request.stock(),
          request.category().trim()
  );

  return ProductResponse.from(repo.save(product));
 }

 // Update a product
 public ProductResponse update(
         Long id,
         ProductRequest request) {

  Product product = repo.findById(id)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Product not found: " + id));

  product.setName(request.name().trim());
  product.setDescription(request.description());
  product.setPrice(request.price());
  product.setStock(request.stock());
  product.setCategory(request.category().trim());

  return ProductResponse.from(repo.save(product));
 }

 // Delete a product
 public void delete(Long id) {
  if (!repo.existsById(id)) {
   throw new ResourceNotFoundException(
           "Product not found: " + id);
  }

  repo.deleteById(id);
 }
}
