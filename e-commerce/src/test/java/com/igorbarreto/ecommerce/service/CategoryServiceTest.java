package com.igorbarreto.ecommerce.service;

import com.igorbarreto.ecommerce.domain.category.Category;
import com.igorbarreto.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Create a category should return the category created")
    public void createCategory_Case1() {

        Category category = new Category("tecnologies");

        when(categoryRepository.save(category)).thenReturn(category);

        var categoryCreated = categoryService.createCategory(category);

        assertNotNull(category);
        assertEquals(category.getName(), categoryCreated.getName());

    }

    @Test
    @DisplayName("Should get all categories")
    public void getAll_Categories_Case1() {

        Category category1 = new Category("cate1");
        Category category2 = new Category("cate2");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<Category> allCategories = categoryService.findAllCategories();

        assertNotNull(allCategories);
        assertEquals(allCategories.get(0), category1);
        assertEquals(allCategories.get(1), category2);
    }

}
