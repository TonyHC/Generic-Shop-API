package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.api.model.CategoryListDTO;
import com.springframework.springmvcrest.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
* @RestController: convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
* @ResponseBody activated by default (no need to add the annotation for each request mapping) and indicates that the
* return type should be written straight to the HTTP response body (and not placed in a Model, or interpreted as a view name)
*/
@RestController
@RequestMapping(CategoryController.CATEGORY_BASE_URL)
public class CategoryController {
    public static final String CATEGORY_BASE_URL = "/api/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*
    * ResponseEntity is meant to represent the entire HTTP response
    * You can control anything that goes into it: status code, headers, and body
    * CategoryListDTO object is written to the HTTP Response Entity
    */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}