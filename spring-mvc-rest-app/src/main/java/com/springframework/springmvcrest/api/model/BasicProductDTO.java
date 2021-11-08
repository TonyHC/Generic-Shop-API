package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicProductDTO {
    @ApiModelProperty(value = "Id of basic product")
    private Long id;

    @ApiModelProperty(value = "Name of basic product")
    private String name;

    @ApiModelProperty(value = "URL of basic product")
    @JsonProperty("product_url")
    private String productUrl;
}