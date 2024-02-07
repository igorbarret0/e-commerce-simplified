package com.igorbarreto.ecommerce.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.igorbarreto.ecommerce.domain.user.User;
import com.igorbarreto.ecommerce.service.JwtService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET = "LASJDAKSJD28234829342439";
    @Override
    public String generateToken(User user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            String token = JWT.create()
                    .withIssuer("e-commerce")
                    .withSubject(user.getLogin())
                    .withExpiresAt(tokenExpiresAt())
                    .sign(algorithm);
            return token;

        } catch(JWTCreationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public String verifyToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.
                    require(algorithm)
                    .withIssuer("e-commerce")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch(JWTVerificationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }


    private Instant tokenExpiresAt() {

        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));

    }

}
