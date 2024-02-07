package com.igorbarreto.ecommerce.dtos;

public record UserPurchaseResponseDTO(

        String buyerName,
        Double moneyAccount,
        String message
) {
}
