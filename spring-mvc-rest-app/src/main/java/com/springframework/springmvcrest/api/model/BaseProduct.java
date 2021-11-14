package com.springframework.springmvcrest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BaseProduct {
    @Schema(description = "Id of product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of product", required = true, example = "Tangerine")
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
}