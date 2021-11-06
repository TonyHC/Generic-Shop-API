package com.springframework.springmvcrest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductListDTO {
    @ApiModelProperty(value = "List of basic products info")
    List<BasicProductDTO> products;
}