package com.igorbarreto.ecommerce.controller;

import com.igorbarreto.ecommerce.domain.category.Category;
import com.igorbarreto.ecommerce.service.CategoryService;
import com.igorbarreto.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {

        var categoryCreated = categoryService.createCategory(category);

        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {

        List<Category> allCategories = categoryService.findAllCategories();

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

}
