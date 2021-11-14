package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDTO extends BaseProduct {
    @Schema(description = "Price of product", required = true, example = "3.50")
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(description = "Endpoint to find vendor", required = true, example = "/api/vendors/2")
    @JsonProperty(value = "vendor_url")
    @NotBlank
    private String vendorUrl;

    @Schema(description = "Endpoint to find category", required = true, example = "/api/categories/4")
    @JsonProperty(value = "category_url")
    @NotBlank
    private String categoryUrl;
}