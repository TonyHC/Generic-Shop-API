package com.springframework.springmvcrest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListDTO {
    @ApiModelProperty(value = "List of customers")
    List<CustomerDTO> customers;
}