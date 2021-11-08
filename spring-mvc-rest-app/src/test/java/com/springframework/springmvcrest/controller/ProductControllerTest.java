package com.springframework.springmvcrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;
import com.springframework.springmvcrest.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {
    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Grapes");
    }

    @Test
    void getAllProducts() throws Exception {
        ProductListDTO productListDTO = new ProductListDTO(Arrays.asList(new BasicProductDTO(), new BasicProductDTO()));

        given(productService.getAllProducts()).willReturn(productListDTO);

        mockMvc.perform(get(ProductController.PRODUCT_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    void getProductById() throws Exception {
        given(productService.getProductById(anyLong())).willReturn(productDTO);

        mockMvc.perform(get(ProductController.PRODUCT_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", containsString(productDTO.getName())));
    }

    @Test
    void createNewProduct() throws Exception {
        given(productService.createNewProduct(productDTO)).willReturn(productDTO);

        mockMvc.perform(post(ProductController.PRODUCT_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(productDTO.getName())));
    }

    @Test
    void updateProduct() throws Exception {
        given(productService.updateProduct(anyLong(), any(ProductDTO.class))).willReturn(productDTO);

        mockMvc.perform(put(ProductController.PRODUCT_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(productDTO.getName())));
    }

    @Test
    void patchProduct() throws Exception {
        given(productService.patchProduct(anyLong(), any(ProductDTO.class))).willReturn(productDTO);

        mockMvc.perform(patch(ProductController.PRODUCT_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(productDTO.getName())));
    }

    @Test
    void deleteProductById() throws Exception {
        mockMvc.perform(delete(ProductController.PRODUCT_BASE_URL + "/1"))
                .andExpect(status().isOk());

        then(productService).should(times(1)).deleteProductById(anyLong());
    }
}