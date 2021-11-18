package com.springframework.springmvcrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.springmvcrest.api.model.CustomerDTO;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.exception.RestResponseExceptionHandler;
import com.springframework.springmvcrest.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "Wayne";
    public static final String LAST_NAME = "Hone";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;
    CustomerDTO customerDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseExceptionHandler()).build();

        customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setFirstName(FIRST_NAME);
    }

    @Test
    void getAllCustomersTest() throws Exception {
        // Given
        List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO(), customerDTO);

        // When
        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        // Then
        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)))
                .andExpect(jsonPath("$.customers[2].firstName", equalTo(FIRST_NAME)));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        // When
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        // Then
        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
    }

    @Test
    void getCustomerByIdNotFoundTest() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerByIdInvalidIdTest() throws Exception {
        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/se")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewCustomerTest() throws Exception {
        // Given
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customerDTO.getId());
        returnCustomerDTO.setFirstName(customerDTO.getFirstName());
        returnCustomerDTO.setLastName(customerDTO.getLastName());

        // When
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(post(CustomerController.CUSTOMER_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void updateCustomerTest() throws Exception {
        // Given
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customerDTO.getId());
        returnCustomerDTO.setFirstName(customerDTO.getFirstName());
        returnCustomerDTO.setLastName(customerDTO.getLastName());

        // When
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(put(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void patchCustomerTest() throws Exception {
        // Given
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customerDTO.getId());
        returnCustomerDTO.setFirstName("Marcus");
        returnCustomerDTO.setLastName(customerDTO.getLastName());

        // When
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(patch(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Marcus")))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}