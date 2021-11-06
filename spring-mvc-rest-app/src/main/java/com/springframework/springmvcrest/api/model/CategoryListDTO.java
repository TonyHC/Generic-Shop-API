package com.springframework.springmvcrest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO {
    @ApiModelProperty(value = "List of categories")
    List<CategoryDTO> categories;
}