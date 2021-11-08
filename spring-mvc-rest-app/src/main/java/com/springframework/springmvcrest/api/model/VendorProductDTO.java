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
public class VendorProductDTO {
    @ApiModelProperty(value = "Id of vendor product")
    private Long id;

    @ApiModelProperty(value = "Name of vendor product", required = true)
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    @ApiModelProperty(value = "Price of vendor product", required = true)
    @NotNull
    @Positive
    private BigDecimal price;

    @ApiModelProperty(value = "Category of vendor product", required = true)
    @JsonProperty(value = "category")
    @NotBlank
    @Size(min = 1, max = 30)
    private String categoryName;
}