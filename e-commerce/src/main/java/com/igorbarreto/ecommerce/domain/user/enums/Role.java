package com.igorbarreto.ecommerce.domain.user.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"),
    USER("user");

    Role(String user) {
    }

}
