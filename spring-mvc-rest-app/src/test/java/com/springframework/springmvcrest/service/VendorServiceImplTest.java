package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.ProductMapper;
import com.springframework.springmvcrest.api.mapper.VendorMapper;
import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.domain.Product;
import com.springframework.springmvcrest.domain.Vendor;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {
    public static final long ID = 1L;
    public static final String NAME = "Hue";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE, ProductMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        // Given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        // Mockito BDD Syntax
        given(vendorRepository.findAll()).willReturn(vendors);

        // When
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        // Then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorDTOS.size(), is(equalTo(2)));
    }

    @Test
    void getVendorById() {
        // Given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        // When
        VendorDTO vendorDTO = vendorService.getVendorById(anyLong());

        // Then
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertThat(vendorDTO.getName(), is(equalTo(NAME)));
    }

    @Test
    void getVendorByIdNotFound() {
        // Given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        Executable executable = () -> vendorService.getVendorById(anyLong());

        // Then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void getVendorProductsById() {
        // Given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        vendor.addProduct(new Product());
        vendor.addProduct(new Product());

        given(vendorRepository.findVendorProductsById(anyLong())).willReturn(Optional.of(vendor));

        // When
        VendorListProductsDTO vendorListProductsDTO = vendorService.getVendorProductsById(anyLong());

        // Then
        then(vendorRepository).should(times(1)).findVendorProductsById(anyLong());
        assertThat(vendorListProductsDTO.getProducts().size(), is(equalTo(2)));
    }

    @Test
    void createNewVendor() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        // When
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        // Then
        then(vendorRepository).should(times(1)).save(any(Vendor.class));

        assertThat(savedVendorDTO.getId(), is(equalTo(1L)));
        assertThat(savedVendorDTO.getName(), containsString(vendor.getName()));
    }

    @Test
    void createNewVendorProduct() {
        // Given
        VendorProductDTO vendorProductDTO = new VendorProductDTO();
        vendorProductDTO.setId(1L);
        vendorProductDTO.setName("Apple");
        vendorProductDTO.setCategory("Fresh");
        vendorProductDTO.setPrice(new BigDecimal("0.99"));

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        vendor.addProduct(ProductMapper.INSTANCE.vendorProductDTOtoProduct(vendorProductDTO));

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        // When
        VendorProductDTO savedVendorProductDTO = vendorService.createNewVendorProduct(1L, vendorProductDTO);

        // Then
        then(vendorRepository).should(times(1)).save(any(Vendor.class));

        assertThat(savedVendorProductDTO.getName(), containsString(vendorProductDTO.getName()));
        assertThat(savedVendorProductDTO.getPrice(), is(equalTo(vendorProductDTO.getPrice())));
    }

    @Test
    void saveVendorByDTO() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        // When
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        // Then
        then(vendorRepository).should(times(1)).save(any(Vendor.class));

        assertThat(savedVendorDTO.getName(), containsString(vendor.getName()));
    }

    @Test
    void patchVendor() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        // When
        VendorDTO patchVendorDTO = vendorService.patchVendor(ID, vendorDTO);

        // Then
        then(vendorRepository).should(times(1)).save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertThat(patchVendorDTO.getName(), containsString(vendor.getName()));
    }

    @Test
    void deleteVendorById() {
        // Given
        Long vendorIdToDelete = 3L;

        // When
        vendorService.deleteVendorById(vendorIdToDelete);

        // Then
        then(vendorRepository).should(times(1)).deleteById(anyLong());
    }
}