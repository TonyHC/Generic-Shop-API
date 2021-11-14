package com.springframework.springmvcrest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {
    @Schema(description = "List of vendors")
    List<VendorDTO> vendors;
}