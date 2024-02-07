package com.igorbarreto.ecommerce.service.impl;

import com.igorbarreto.ecommerce.domain.user.User;
import com.igorbarreto.ecommerce.dtos.UserRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserResponseDTO;
import com.igorbarreto.ecommerce.repository.UserRepository;
import com.igorbarreto.ecommerce.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        var userExists = userRepository.findByLogin(request.login());

        if (userExists != null) {
            throw new RuntimeException("User already registered");
        }

        var user = new User(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        this.userRepository.save(user);

        return new UserResponseDTO(request.name(), request.login());

    }
}
