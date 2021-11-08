package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    @ApiModelProperty(value = "Id of product")
    private Long id;

    @ApiModelProperty(value = "Name of product", required = true)
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    @ApiModelProperty(value = "Price of product", required = true)
    @NotNull
    @Positive
    private BigDecimal price;

    @JsonProperty(value = "vendor_url")
    @ApiModelProperty(value = "GET Mapping to find product", required = true)
    @NotBlank
    private String vendorUrl;

    @JsonProperty(value = "category_url")
    @ApiModelProperty(value = "GET Mapping to find category", required = true)
    @NotBlank
    private String categoryUrl;
}