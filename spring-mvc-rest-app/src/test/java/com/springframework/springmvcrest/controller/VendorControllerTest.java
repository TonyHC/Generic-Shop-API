package com.springframework.springmvcrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
/*
* Often @WebMvcTest will be limited to a single controller and used in combination with @MockBean to provide mock
* implementations for required collaborators.
*/
@WebMvcTest(controllers = {VendorController.class})
class VendorControllerTest {
    /*
    * It allows to us add Mockito mock(s) to a Spring Application Context
    * If a Bean, compatible with the declared class exists in the context, it's replace by the Mock
    * If it is not the case, it adds the Mock to the Spring Application Context as a Bean
    * If your test needs to rely on the Spring Boot Container and you want also to Add or Mock
    * one of the Spring Container Beans: use @MockBean
    * Otherwise, use @Mock, it is fast and favors the isolation of the tested component
    */
    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO;
    VendorProductDTO vendorProductDTO;

    @BeforeEach
    void setup() {
        vendorDTO = new VendorDTO();
        vendorDTO.setName("Vendor 1");
        vendorDTO.setVendorUrl(VendorController.VENDOR_BASE_URL + "/1");

        vendorProductDTO = new VendorProductDTO();
        vendorProductDTO.setName("Orange");
        vendorProductDTO.setPrice(new BigDecimal("0.50"));
        vendorProductDTO.setCategoryName("Fruits");
    }

    @Test
    void getAllVendors() throws Exception {
        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO, vendorDTO));

        given(vendorService.getAllVendors()).willReturn(vendorListDTO.getVendors());

        mockMvc.perform(get(VendorController.VENDOR_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorById() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO);

        mockMvc.perform(get(VendorController.VENDOR_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
    }

    @Test
    void getVendorProductsById() throws Exception {
        VendorListProductsDTO vendorListProductsDTO = new VendorListProductsDTO();
        vendorListProductsDTO.setProducts(Arrays.asList(vendorProductDTO, vendorProductDTO));

        given(vendorService.getVendorProductsById(anyLong())).willReturn(vendorListProductsDTO);

        mockMvc.perform(get(VendorController.VENDOR_BASE_URL + "/1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    void createNewVendor() throws Exception {
        given(vendorService.createNewVendor(vendorDTO)).willReturn(vendorDTO);

        mockMvc.perform(post(VendorController.VENDOR_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
    }

    @Test
    void createNewVendorProduct() throws Exception {
        given(vendorService.createNewVendorProduct(anyLong(), any(VendorProductDTO.class))).willReturn(vendorProductDTO);

        mockMvc.perform(post(VendorController.VENDOR_BASE_URL + "/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vendorProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category", equalTo(vendorProductDTO.getCategoryName())));
    }

    @Test
    void updateVendor() throws Exception {
        given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO);

        mockMvc.perform(put((VendorController.VENDOR_BASE_URL + "/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
    }

    @Test
    void patchVendor() throws Exception {
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO);

        mockMvc.perform(patch(VendorController.VENDOR_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.VENDOR_BASE_URL + "/1"))
                .andExpect(status().isOk());

        then(vendorService).should(times(1)).deleteVendorById(anyLong());
    }
}