package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class VendorProductDTO extends BaseProduct {
    @ApiModelProperty(value = "Price of vendor product", required = true, example = "6.50", position = 2)
    @NotNull
    @Positive
    private BigDecimal price;

    @ApiModelProperty(value = "Category of vendor product", required = true, example = "Fresh", position = 3)
    @JsonProperty(value = "category")
    @NotBlank
    @Size(min = 1, max = 30)
    private String categoryName;
}