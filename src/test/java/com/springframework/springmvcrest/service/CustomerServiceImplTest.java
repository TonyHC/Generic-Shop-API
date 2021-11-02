package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.CustomerMapper;
import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.domain.Customer;
import com.springframework.springmvcrest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    public static final long ID = 1L;
    public static final String WAYNE = "Wayne";
    public static final String LAST_NAME = "Hone";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomers() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(ID);
        customer1.setFirstName(WAYNE);
        customer1.setLastName(LAST_NAME);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Jack");
        customer2.setLastName("Poe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // When
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        // Then
        assertEquals(2, customerDTOList.size());
    }

    @Test
    void getCustomerById() {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(WAYNE);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // When
        CustomerDTO customerDTO = customerService.getCustomerById(anyLong());

        // Then
        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer.getLastName(), customerDTO.getLastName());
    }
}