package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.user.User;
import com.igorbarreto.ecommerce.domain.user.enums.Role;
import com.igorbarreto.ecommerce.dtos.UserRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserResponseDTO;
import com.igorbarreto.ecommerce.repository.UserRepository;
import com.igorbarreto.ecommerce.service.impl.UserServiceImpl;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    UserRequestDTO userRequest = new UserRequestDTO("John Doe", "john.doe@example.com",
            "password123", 100.0, Role.USER);


    @Test
    @DisplayName("When registering a new user, should return a valid UserResponseDTO")
    public void register_NewUser_ReturnsUserResponseDTO() {


        when(userRepository.findByLogin(userRequest.login())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Retorna o objeto passado como argumento

        when(passwordEncoder.encode(userRequest.password())).thenReturn("encodedPassword");

        UserResponseDTO userResponse = userServiceImpl.register(userRequest);

        assertNotNull(userResponse);
        assertEquals(userRequest.name(), userResponse.name());
        assertEquals(userRequest.login(), userResponse.login());
    }

    @Test
    @DisplayName("When register a person who is already registered")
    public void register_UserExists_ThrowException() {

      String existingLogin = "existingUser";
      User existingUser = new User("Existing User", existingLogin, "existingPassword", Role.USER);

      when(userRepository.findByLogin(existingLogin)).thenReturn(existingUser);

      assertThrows(RuntimeException.class, () -> {

          userServiceImpl.register(userRequest);
      });
    };
}



