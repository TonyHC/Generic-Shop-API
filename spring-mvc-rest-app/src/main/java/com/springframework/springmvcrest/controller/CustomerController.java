package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.api.model.CustomerListDTO;
import com.springframework.springmvcrest.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Customer", description = "Customer API")
@RestController
@RequestMapping(CustomerController.CUSTOMER_BASE_URL)
public class CustomerController {
    public static final String CUSTOMER_BASE_URL = "/api/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "List of customers", notes = "Return a small amount of Customers")
    @ApiResponse(code = 200, message = "Successfully retrieved customers",
            response = CustomerListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @ApiOperation(value = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Customer not found") })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @ApiOperation(value = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new customer"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "Update existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated customer"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Customer not found") })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @ApiOperation(value = "Patch existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Customer not found") })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @ApiOperation(value = "Delete existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted customer"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Customer not found") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}