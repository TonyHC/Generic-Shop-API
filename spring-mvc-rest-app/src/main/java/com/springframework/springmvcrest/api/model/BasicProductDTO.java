package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicProductDTO extends BaseProduct {
    @ApiModelProperty(value = "URL of basic product", position = 2)
    @JsonProperty("product_url")
    private String productUrl;
}