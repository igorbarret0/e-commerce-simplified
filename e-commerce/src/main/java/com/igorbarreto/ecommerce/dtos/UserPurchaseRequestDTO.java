package com.igorbarreto.ecommerce.dtos;

public record UserPurchaseRequestDTO(
        Long productId,
        String buyerLogin
) {
}
