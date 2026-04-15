package com.shopkart.shopkart.service;

import com.shopkart.shopkart.dto.response.ProductResponse;
import com.shopkart.shopkart.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsByCategory(Long categoryId);
    List<ProductResponse> searchProducts(String name);
    ProductResponse createProduct(Product product);
    ProductResponse updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}