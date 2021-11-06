package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.ProductMapper;
import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.domain.Product;
import com.springframework.springmvcrest.domain.Vendor;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    public static final long ID = 1L;
    public static final String NAME = "Banana";
    public static final String CATEGORY = "Fruits";
    public static final String PRICE = "1.22";

    @Mock
    ProductRepository productRepository;

    ProductService productService;

    Product product;
    ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, ProductMapper.INSTANCE);

        product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setCategory(CATEGORY);
        product.setPrice(new BigDecimal(PRICE));
        product.setVendor(new Vendor());

        productDTO = new ProductDTO();
        productDTO.setId(ID);
        productDTO.setName(NAME);
        productDTO.setPrice(new BigDecimal(PRICE));
    }

    @Test
    void getAllProducts() {
        // Given
        List<Product> products = Arrays.asList(new Product(), new Product());

        given(productRepository.findAll()).willReturn(products);

        // When
        List<BasicProductDTO> basicProductDTOS = productService.getAllProducts();

        // Then
        assertThat(basicProductDTOS.size(), is(equalTo(2)));
    }

    @Test
    void getProductById() {
        // Given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // When
        ProductDTO productDTO = productService.getProductById(anyLong());

        // Then
        assertThat(productDTO.getName(), is(equalTo(NAME)));
        assertThat(product.getCategory(), is(equalTo(CATEGORY)));
    }

    @Test
    void getProductByIdNotFound() {
        // Given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        Executable executable = () -> productService.getProductById(anyLong());

        // Then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void createNewProduct() {
        // Given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        ProductDTO saveProductDTO = productService.createNewProduct(productDTO);

        // Then
        assertThat(saveProductDTO.getName(), is(equalTo(productDTO.getName())));
    }

    @Test
    void updateProduct() {
        // Given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        ProductDTO saveProductDTO = productService.updateProduct(anyLong(), productDTO);

        // Then
        assertThat(saveProductDTO.getName(), is(equalTo(productDTO.getName())));
    }

    @Test
    void patchProduct() {
        // Given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        ProductDTO savedProductDTO = productService.patchProduct(anyLong(), productDTO);

        // Then
        assertThat(savedProductDTO.getName(), containsString(NAME));
        assertThat(savedProductDTO.getPrice().toString(), containsString(productDTO.getPrice().toString()));
    }

    @Test
    void deleteProductById() {
        // Given
        Long productIdToBeDeleted = 2L;

        // When
        productService.deleteProductById(productIdToBeDeleted);

        // Then
        then(productRepository).should(times(1)).deleteById((anyLong()));
    }
}