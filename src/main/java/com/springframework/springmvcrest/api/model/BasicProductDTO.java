package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasicProductDTO {
    private Long id;
    private String name;

    @JsonProperty("product_url")
    private String productUrl;
}