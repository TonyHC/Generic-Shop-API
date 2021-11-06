package com.springframework.springmvcrest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    @ApiModelProperty(value = "Id of category")
    private Long id;

    @ApiModelProperty(value = "Name of the category")
    private String name;
}