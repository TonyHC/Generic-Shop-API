package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @ApiModelProperty(value = "Id of vendor")
    private Long id;

    @ApiModelProperty(value = "Name of the vendor")
    private String name;

    @ApiModelProperty(value = "GET mapping to find vendor")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}