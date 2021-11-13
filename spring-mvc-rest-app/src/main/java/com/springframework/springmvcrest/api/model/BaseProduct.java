package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BaseProduct {
    @ApiModelProperty(value = "Id of product", position = 0)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Id of product", required = true, example = "Tangerine", position = 1)
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
}