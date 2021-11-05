package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.VendorMapper;
import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.controller.VendorController;
import com.springframework.springmvcrest.domain.Vendor;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(id));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListProductsDTO getVendorProductsById(Long id) {
        return vendorRepository.findVendorProductsById(id)
                .map(vendorMapper::vendorToVendorProductsDTO)
                .map(vendorProductsDTO -> {
                    vendorProductsDTO.getProducts().forEach(productDTO -> {
                        productDTO.setVendorUrl(getVendorUrl(id));
                    });
                    return vendorProductsDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnVendorDTO(vendorMapper.vendorDTOtoVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnVendorDTO.setVendorUrl(getVendorUrl(id));
            return returnVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnVendorDTO;
    }

    private String getVendorUrl(Long id) {
        return VendorController.VENDOR_BASE_URL + "/" + id;
    }
}