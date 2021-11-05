package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);
    Vendor vendorDTOtoVendor(VendorDTO vendorDTO);

    VendorListProductsDTO vendorToVendorProductsDTO(Vendor vendor);
}