package com.igorbarreto.ecommerce.config;


import com.igorbarreto.ecommerce.domain.user.User;
import com.igorbarreto.ecommerce.repository.UserRepository;
import com.igorbarreto.ecommerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UserRepository userRepository;

    public SecurityFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenHeader(request);

        if (token != null) {
            String login = jwtService.verifyToken(token);

            User user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);

    }

    public String extractTokenHeader(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return  null;
        }

        if (!authHeader.split(" ")[0].equals("Bearer")) {
            return null;
        }

        return authHeader.split(" ")[1];
    }

}
