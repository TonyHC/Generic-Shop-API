package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "Id of customer")
    private Long id;

    @ApiModelProperty(value = "First Name", required = true)
    private String firstName;

    @ApiModelProperty(value = "Last Name", required = true)
    private String lastName;

    @ApiModelProperty(value = "GET mapping to find customer")
    @JsonProperty("customer_url")
    private String customerUrl;
}