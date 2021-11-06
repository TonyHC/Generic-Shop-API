package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "Thomas";
    public static final String LAST_NAME = "Payne";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        // When
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // Then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    void customerDTOtoCustomer() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        // When
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);

        // Then
        assertEquals(ID, customer.getId());
        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
    }
}