package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorDTO {
    private Long id;
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;
}