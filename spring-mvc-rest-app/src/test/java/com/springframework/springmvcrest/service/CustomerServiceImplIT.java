package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.CustomerMapper;
import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.domain.Customer;
import com.springframework.springmvcrest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomerUpdatedFirstName() {
        // Given
        String updatedName = "UpdatedName";
        Long customerId = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(customerId).get();

        String originalCustomerFirstName = originalCustomer.getFirstName();
        String originalCustomerLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        // When
        customerService.patchCustomer(customerId, customerDTO);

        // Then
        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalCustomerFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalCustomerLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    void patchCustomerUpdatedLastName() {
        // Given
        String updatedName = "UpdatedName";
        Long customerId = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(customerId).get();

        String originalCustomerFirstName = originalCustomer.getFirstName();
        String originalCustomerLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        // When
        customerService.patchCustomer(customerId, customerDTO);

        // Then
        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalCustomerFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalCustomerLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();

        return customers.get(0).getId();
    }
}