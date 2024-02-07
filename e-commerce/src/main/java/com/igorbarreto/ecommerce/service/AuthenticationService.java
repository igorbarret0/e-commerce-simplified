package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.dtos.AuthDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {

    public String getToken(AuthDTO authDTO);
}
