package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorProductDTO {
    @ApiModelProperty(value = "Id of vendor product")
    private Long id;

    @ApiModelProperty(value = "Name of vendor product")
    private String name;

    @ApiModelProperty(value = "Price of vendor product")
    private BigDecimal price;

    @ApiModelProperty(value = "Category of vendor product")
    @JsonProperty(value = "category")
    private String categoryName;
}