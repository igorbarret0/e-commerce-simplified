package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.category.Category;
import com.igorbarreto.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category createCategory(Category category) {

        var categoryCreated = categoryRepository.save(category);
        return categoryCreated;
    }

    public List<Category> findAllCategories() {

        return categoryRepository.findAll();
    }

}
