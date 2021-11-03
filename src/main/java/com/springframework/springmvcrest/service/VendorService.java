package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.model.VendorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}