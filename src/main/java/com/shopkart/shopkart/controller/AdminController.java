package com.shopkart.shopkart.controller;

import com.shopkart.shopkart.dto.response.OrderResponse;
import com.shopkart.shopkart.dto.response.ProductResponse;
import com.shopkart.shopkart.entity.Category;
import com.shopkart.shopkart.entity.Product;
import com.shopkart.shopkart.exception.BadRequestException;
import com.shopkart.shopkart.exception.ResourceNotFoundException;
import com.shopkart.shopkart.repository.CategoryRepository;
import com.shopkart.shopkart.service.OrderService;
import com.shopkart.shopkart.service.ProductService;
import com.shopkart.shopkart.util.ApiResponse;
import com.shopkart.shopkart.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryRepository categoryRepository;

    // ─── Category Endpoints ───────────────────────────────────────────

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new BadRequestException("Category already exists");
        }
        Category saved = categoryRepository.save(category);
        return new ResponseEntity<>(ApiResponse.success("Category created successfully", saved), HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categories));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id,
                                                                @RequestBody Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND));
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        Category updated = categoryRepository.save(existing);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", updated));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable Long id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND));
        categoryRepository.delete(existing);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully"));
    }

    // ─── Product Endpoints ────────────────────────────────────────────

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody Product product) {
        ProductResponse created = productService.createProduct(product);
        return new ResponseEntity<>(ApiResponse.success("Product created successfully", created), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id,
                                                                      @RequestBody Product product) {
        ProductResponse updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updated));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully"));
    }

    // ─── Order Endpoints ──────────────────────────────────────────────

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Orders fetched successfully", orders));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId,
                                                                        @RequestParam String status) {
        OrderResponse order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", order));
    }
}