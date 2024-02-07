package com.igorbarreto.ecommerce.dtos;

import com.igorbarreto.ecommerce.domain.user.enums.Role;

public record UserRequestDTO(
        String name,
        String login,
        String password,
        Double moneyAccount,
        Role role
) {
}
