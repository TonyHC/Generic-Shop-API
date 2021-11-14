package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.api.model.CustomerListDTO;
import com.springframework.springmvcrest.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Customer", description = "Customer API")
@RestController
@RequestMapping(CustomerController.CUSTOMER_BASE_URL)
public class CustomerController {
    public static final String CUSTOMER_BASE_URL = "/api/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "List of customers", description = "Return a small subset of customers")
    @ApiResponse(responseCode = "200", description = "Found customers",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CustomerListDTO.class))))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @Operation(summary = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found customer",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@Parameter(description = "Customer id", required = true) @PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new customer",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @Operation(summary = "Update existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated customer",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer not found", content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@Parameter(description = "Customer id", required = true) @PathVariable Long id,
                                      @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @Operation(summary = "Patch existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patched customer",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer not found", content = @Content)})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@Parameter(description = "Customer id", required = true) @PathVariable Long id,
                                     @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @Operation(summary = "Delete existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@Parameter(description = "Customer id", required = true) @PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}