package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class VendorDTO {
    @ApiModelProperty(value = "Id of vendor", position = 0)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name of the vendor", required = true, example = "Fresh Fruits", position = 1)
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @ApiModelProperty(value = "GET mapping to find vendor", example = "/api/vendors/3", position = 2)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}