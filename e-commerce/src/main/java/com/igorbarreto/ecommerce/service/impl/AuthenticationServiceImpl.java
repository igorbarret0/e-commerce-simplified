package com.igorbarreto.ecommerce.service.impl;

import com.igorbarreto.ecommerce.dtos.AuthDTO;
import com.igorbarreto.ecommerce.repository.UserRepository;
import com.igorbarreto.ecommerce.service.AuthenticationService;
import com.igorbarreto.ecommerce.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    private JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    @Override
    public String getToken(AuthDTO authDTO) {

        var user = userRepository.findByLogin(authDTO.login());

        return jwtService.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}
