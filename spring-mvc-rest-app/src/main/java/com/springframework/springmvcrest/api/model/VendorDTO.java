package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class VendorDTO {
    @Schema(description = "Id of vendor", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Name of vendor", required = true, example = "Fresh Fruits")
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Schema(description = "Endpoint to find vendor", example = "/api/vendors/3")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}