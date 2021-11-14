package com.springframework.springmvcrest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListDTO {
    @Schema(description = "List of customers")
    List<CustomerDTO> customers;
}