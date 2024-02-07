package com.igorbarreto.ecommerce.controller;

import com.igorbarreto.ecommerce.dtos.UserPurchaseRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserPurchaseResponseDTO;
import com.igorbarreto.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private ProductService productService;

    public SalesController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<UserPurchaseResponseDTO> buyProduct(@RequestBody UserPurchaseRequestDTO request) {

        UserPurchaseResponseDTO response = productService.buyProduct(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
