package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class VendorProductDTO extends BaseProduct {
    @Schema(description = "Price of vendor product", required = true, example = "3.99")
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(description = "Category of vendor product", required = true, example = "Fresh")
    @JsonProperty(value = "category")
    @NotBlank
    @Size(min = 1, max = 30)
    private String categoryName;
}