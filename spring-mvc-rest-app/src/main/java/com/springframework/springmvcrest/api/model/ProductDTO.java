package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDTO extends BaseProduct {
    @ApiModelProperty(value = "Price of product", required = true, example = "4.99", position = 2)
    @NotNull
    @Positive
    private BigDecimal price;

    @JsonProperty(value = "vendor_url")
    @ApiModelProperty(value = "GET Mapping to find product", required = true, example = "/api/products/2", position = 3)
    @NotBlank
    private String vendorUrl;

    @JsonProperty(value = "category_url")
    @ApiModelProperty(value = "GET Mapping to find category", required = true, example = "/api/products/4", position = 4)
    @NotBlank
    private String categoryUrl;
}