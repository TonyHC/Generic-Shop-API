package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasicProductDTO extends BaseProduct {
    @Schema(description = "Endpoint to find product", example = "/api/products/2")
    @JsonProperty("product_url")
    private String productUrl;
}