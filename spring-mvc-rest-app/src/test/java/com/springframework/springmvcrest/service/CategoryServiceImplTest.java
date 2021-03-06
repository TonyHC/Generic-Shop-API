package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.CategoryMapper;
import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.domain.Category;
import com.springframework.springmvcrest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    public static final String NAME = "Thomas";
    public static final long ID = 1L;

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {
        // Given
        List<Category> categories = Arrays.asList(new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        // Then
        assertEquals(2, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        // Given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // When
        CategoryDTO categoryDTO = categoryService.getCategoryById(ID);

        // Then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}