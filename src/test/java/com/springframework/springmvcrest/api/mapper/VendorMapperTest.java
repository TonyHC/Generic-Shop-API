package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {
    public static final long ID = 1L;
    public static final String NAME = "Marcus";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        // Given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        // When
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // Then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void vendorDTOtoVendor() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        // When
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);

        // Then
        assertEquals(ID, vendor.getId());
        assertEquals(NAME, vendor.getName());
    }
}