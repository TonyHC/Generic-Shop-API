package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {
    final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTOTest() {
        // Given
        Category category = new Category();
        category.setName("Thomas");
        category.setId(1L);

        // When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // Then
        assertEquals(Long.valueOf(1L), categoryDTO.getId());
        assertEquals("Thomas", categoryDTO.getName());
    }
}