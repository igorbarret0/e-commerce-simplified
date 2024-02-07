package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.dtos.UserRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserResponseDTO;

public interface UserService {

    public UserResponseDTO register(UserRequestDTO request);
}
