package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CategoryDTO;
import com.springframework.springmvcrest.api.model.CategoryListDTO;
import com.springframework.springmvcrest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "Category API")
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

    @Operation(summary = "List all categories")
    /*
    * ResponseEntity is meant to represent the entire HTTP response
    * You can control anything that goes into it: status code, headers, and body
    * CategoryListDTO object is written to the HTTP Response Entity
    */
    @ApiResponse(responseCode = "200", description = "Found categories", content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CategoryListDTO.class))))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @Operation(summary = "Get category by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found category",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@Parameter(description = "Category id", required = true) @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}