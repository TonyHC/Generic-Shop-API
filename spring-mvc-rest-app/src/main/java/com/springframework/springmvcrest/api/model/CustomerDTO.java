package com.springframework.springmvcrest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO {
    @Schema(description = "Id of customer", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "First name", required = true, example = "Thomas")
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "First name")
    @Size(min = 1, max = 30)
    private String firstName;

    @Schema(description = "Last name", required = true, example = "Long")
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name")
    @Size(min = 1, max = 30)
    private String lastName;

    @Schema(description = "Endpoint to find customer", example = "/api/customers/2")
    @JsonProperty("customer_url")
    private String customerUrl;
}