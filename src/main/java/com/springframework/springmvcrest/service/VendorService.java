package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorListProductsDTO getVendorProductsById(Long id);
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorProductDTO createNewVendorProduct(Long id, VendorProductDTO vendorProductDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}