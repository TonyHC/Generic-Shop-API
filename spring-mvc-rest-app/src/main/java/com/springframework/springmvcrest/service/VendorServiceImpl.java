package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.ProductMapper;
import com.springframework.springmvcrest.api.mapper.VendorMapper;
import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.controller.VendorController;
import com.springframework.springmvcrest.domain.Product;
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
    private final ProductMapper productMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper, ProductMapper productMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
        this.productMapper = productMapper;
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
                .map(vendorMapper::vendorToVendorListProductsDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnVendorDTO(vendorMapper.vendorDTOtoVendor(vendorDTO));
    }

    @Override
    public VendorProductDTO createNewVendorProduct(Long id, VendorProductDTO vendorProductDTO) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        Product product = productMapper.vendorProductDTOtoProduct(vendorProductDTO);
        product.setCategoryName(vendorProductDTO.getCategoryName());
        vendor.addProduct(product);

        vendorRepository.save(vendor);

        return vendor.getProducts().stream().filter(product1 -> product1.getName().equalsIgnoreCase(product.getName()))
                .map(productMapper::productToVendorProductDTO).findFirst().orElseThrow(ResourceNotFoundException::new);
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