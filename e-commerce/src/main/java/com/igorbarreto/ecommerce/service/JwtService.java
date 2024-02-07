package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.user.User;

public interface JwtService {

    String generateToken(User user);

    String verifyToken(String token);


}
