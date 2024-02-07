package com.igorbarreto.ecommerce.controller;

import com.igorbarreto.ecommerce.dtos.UserRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserResponseDTO;
import com.igorbarreto.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO request) {

        var userResponse = this.userService.register(request);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }


}
