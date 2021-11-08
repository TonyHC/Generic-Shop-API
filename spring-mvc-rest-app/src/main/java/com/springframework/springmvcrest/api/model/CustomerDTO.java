package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "Id of customer")
    private Long id;

    @ApiModelProperty(value = "First Name", required = true)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 1, max = 30)
    private String firstName;

    @ApiModelProperty(value = "Last Name", required = true)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 1, max = 30)
    private String lastName;

    @ApiModelProperty(value = "GET mapping to find customer")
    @JsonProperty("customer_url")
    private String customerUrl;
}