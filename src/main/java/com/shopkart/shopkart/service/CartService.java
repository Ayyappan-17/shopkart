package com.shopkart.shopkart.service;

import com.shopkart.shopkart.dto.request.AddToCartRequest;
import com.shopkart.shopkart.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);
    CartResponse addToCart(Long userId, AddToCartRequest request);
    CartResponse updateCartItem(Long userId, Long cartItemId, Integer quantity);
    void removeFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
}