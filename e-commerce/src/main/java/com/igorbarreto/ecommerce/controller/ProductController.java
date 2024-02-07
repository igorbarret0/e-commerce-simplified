package com.igorbarreto.ecommerce.controller;

import com.igorbarreto.ecommerce.domain.product.Product;
import com.igorbarreto.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {

        var savedProduct = productService.saveProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(
            @RequestBody Product product
    ) {

       var updatedProduct = productService.updateProduct(product);

       return new ResponseEntity<>(updatedProduct, HttpStatus
               .OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {

        var allProducts = productService.findAllProducts();

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @PathVariable(value = "id") Long id
    ) {

        var foundProduct = productService.findProductById(id);

        return new ResponseEntity<>(foundProduct, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(
            @PathVariable(value = "id") Long id
    ) {

        var allProducts = productService.findProductsByCategory(id);

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {

        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
