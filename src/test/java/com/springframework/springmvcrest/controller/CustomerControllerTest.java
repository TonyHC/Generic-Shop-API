package com.springframework.springmvcrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.springmvcrest.api.model.CustomerDTO;
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
import static org.mockito.ArgumentMatchers.*;
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

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        // Given
        List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO());

        // When
        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        // Then
        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        // When
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        // Then
        mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
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
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customer.getId());
        returnCustomerDTO.setFirstName(customer.getFirstName());
        returnCustomerDTO.setLastName(customer.getLastName());

        // When
        when(customerService.createNewCustomer(customer)).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(post(CustomerController.CUSTOMER_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void updateCustomerTest() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customer.getId());
        returnCustomerDTO.setFirstName(customer.getFirstName());
        returnCustomerDTO.setLastName(customer.getLastName());

        // When
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(put(CustomerController.CUSTOMER_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void patchCustomerTest() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setId(customer.getId());
        returnCustomerDTO.setFirstName("Marcus");
        returnCustomerDTO.setLastName(customer.getLastName());

        // When
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        // Then
        mockMvc.perform(patch(CustomerController.CUSTOMER_BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
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