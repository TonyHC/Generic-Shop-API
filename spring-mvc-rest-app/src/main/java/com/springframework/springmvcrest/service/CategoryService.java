package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.domain.Category;

import java.util.List;

public interface CategoryService {
    void save(Category category);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
}