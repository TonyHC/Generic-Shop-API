package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.exception.RestResponseExceptionHandler;
import com.springframework.springmvcrest.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    public static final String NAME = "Thomas";
    public static final long ID = 1L;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;
    CategoryDTO categoryDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseExceptionHandler()).build();

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);
    }

    @Test
    void getAllCategoriesTest() throws Exception {
        // Given
        List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO, categoryDTO);

        // When
        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        // Then
        mockMvc.perform(get(CategoryController.CATEGORY_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Look at the JSON Root and go to the categories property
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    void getCategoryByNameTest() throws Exception {
        // When
        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryDTO);

        // Then
        mockMvc.perform(get(CategoryController.CATEGORY_BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void getCategoryByNameNotFoundTest() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.CATEGORY_BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}