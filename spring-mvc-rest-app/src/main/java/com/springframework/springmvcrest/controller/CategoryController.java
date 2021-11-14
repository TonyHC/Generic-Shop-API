package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.api.model.CategoryListDTO;
import com.springframework.springmvcrest.config.SwaggerConfig;
import com.springframework.springmvcrest.service.CategoryService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {SwaggerConfig.CATEGORY_TAG})
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

    @ApiOperation(value = "List all categories")
    /*
    * ResponseEntity is meant to represent the entire HTTP response
    * You can control anything that goes into it: status code, headers, and body
    * CategoryListDTO object is written to the HTTP Response Entity
    */
    @ApiResponse(code = 200, message = "Found categories",
            response = CategoryListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @ApiOperation(value = "Get category by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found category"),
            @ApiResponse(code = 400, message = "Invalid In supplied"),
            @ApiResponse(code = 404, message = "Category mot found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@ApiParam(value = "Category id", required = true) @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}