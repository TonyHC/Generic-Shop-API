package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "Id of customer", position = 0)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "First Name", required = true, example = "Thomas", position = 1)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 1, max = 30)
    private String firstName;

    @ApiModelProperty(value = "Last Name", required = true, example = "Long", position = 2)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 1, max = 30)
    private String lastName;

    @ApiModelProperty(value = "GET mapping to find customer", example = "/api/customers/2", position = 3)
    @JsonProperty("customer_url")
    private String customerUrl;
}