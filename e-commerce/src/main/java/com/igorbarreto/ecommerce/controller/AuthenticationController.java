package com.igorbarreto.ecommerce.controller;

import com.igorbarreto.ecommerce.dtos.AuthDTO;
import com.igorbarreto.ecommerce.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    private AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDTO request) {

        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        authenticationManager.authenticate(userAuthenticationToken);

        return authenticationService.getToken(request);
    }
}
