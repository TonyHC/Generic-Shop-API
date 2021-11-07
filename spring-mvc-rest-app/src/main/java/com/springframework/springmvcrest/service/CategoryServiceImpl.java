package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.CategoryMapper;
import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.domain.Category;
import com.springframework.springmvcrest.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        //         return categoryMapper.categoryToCategoryDTO(categoryRepository.findById(id).get());
        return categoryRepository.findById(id)
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .findFirst().orElseThrow(ReadOnlyFileSystemException::new);
    }
}