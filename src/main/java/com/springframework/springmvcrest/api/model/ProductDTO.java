package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    @ApiModelProperty(value = "Id of product")
    private Long id;

    @ApiModelProperty(value = "Name of product")
    private String name;

    @ApiModelProperty(value = "Price of product")
    private BigDecimal price;

    @JsonProperty(value = "vendor_url")
    @ApiModelProperty(value = "GET Mapping to find product")
    private String vendorUrl;
}