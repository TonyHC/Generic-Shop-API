package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.ProductMapper;
import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.controller.CategoryController;
import com.springframework.springmvcrest.controller.VendorController;
import com.springframework.springmvcrest.domain.Category;
import com.springframework.springmvcrest.domain.Product;
import com.springframework.springmvcrest.domain.Vendor;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.repository.CategoryRepository;
import com.springframework.springmvcrest.repository.ProductRepository;
import com.springframework.springmvcrest.repository.VendorRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
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

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    VendorRepository vendorRepository;

    ProductService productService;

    Category category;
    Product product;
    ProductDTO productDTO;
    Vendor vendor;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, categoryRepository, vendorRepository, ProductMapper.INSTANCE);

        category = new Category();
        category.setId(ID);

        vendor = new Vendor();
        vendor.setId(ID);

        product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setCategoryName(CATEGORY);
        product.setPrice(new BigDecimal(PRICE));
        product.setVendor(vendor);
        product.setCategory(category);

        productDTO = new ProductDTO();
        productDTO.setId(ID);
        productDTO.setName(NAME);
        productDTO.setPrice(new BigDecimal(PRICE));
        productDTO.setCategoryUrl(CategoryController.CATEGORY_BASE_URL + "/1");
        productDTO.setVendorUrl(VendorController.VENDOR_BASE_URL + "/1");
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
        assertThat(product.getCategoryName(), is(equalTo(CATEGORY)));
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
        given(categoryRepository.findByName(anyString())).willReturn(category);
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        // When
        ProductDTO saveProductDTO = productService.createNewProduct(productDTO);

        // Then
        then(categoryRepository).should(times(1)).findByName(anyString());
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertThat(saveProductDTO.getName(), is(equalTo(productDTO.getName())));
        assertThat(saveProductDTO.getCategoryUrl(), is(equalTo(productDTO.getCategoryUrl())));
        assertThat(saveProductDTO.getVendorUrl(), is(equalTo(productDTO.getVendorUrl())));
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