package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.mapper.ProductMapper;
import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.controller.CategoryController;
import com.springframework.springmvcrest.controller.ProductController;
import com.springframework.springmvcrest.controller.VendorController;
import com.springframework.springmvcrest.domain.Product;
import com.springframework.springmvcrest.exception.ResourceNotFoundException;
import com.springframework.springmvcrest.repository.CategoryRepository;
import com.springframework.springmvcrest.repository.ProductRepository;
import com.springframework.springmvcrest.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, VendorRepository vendorRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<BasicProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    BasicProductDTO basicProductDTO = productMapper.productToBasicProductDTO(product);
                    basicProductDTO.setProductUrl(getProductUrl(product.getId()));
                    return basicProductDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    productDTO.setVendorUrl(getVendorUrl(product.getVendor().getId()));
                    productDTO.setCategoryUrl(getCategoryUrl(product.getCategory().getId()));
                    return productDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOtoProduct(productDTO);

        product.setCategoryName(parseUrlForListing(productDTO.getCategoryUrl()));
        product.setCategory(categoryRepository.findByName(parseUrlForListing(productDTO.getCategoryUrl())));
        product.setVendor(vendorRepository.findById(Long.valueOf(parseUrlForListing(productDTO.getVendorUrl()))).get());

        return saveAndUpdateProduct(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productMapper.productDTOtoProduct(productDTO);
        product.setId(id);

        return saveAndUpdateProduct(product);
    }

    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    if (productDTO.getName() != null) {
                        product.setName(productDTO.getName());
                    }

                    if (productDTO.getPrice() != null) {
                        product.setPrice(productDTO.getPrice());
                    }

                    ProductDTO savedProductDTO = productMapper.productToProductDTO(productRepository.save(product));
                    savedProductDTO.setVendorUrl(getVendorUrl(product.getVendor().getId()));
                    savedProductDTO.setCategoryUrl(getCategoryUrl(product.getCategory().getId()));
                    return savedProductDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private String getProductUrl(Long id) {
        return ProductController.PRODUCT_BASE_URL + "/" + id;
    }

    private String getVendorUrl(Long id) {
        return VendorController.VENDOR_BASE_URL + "/" + id;
    }

    private String getCategoryUrl (Long id) {
        return CategoryController.CATEGORY_BASE_URL + "/" + id;
    }

    private ProductDTO saveAndUpdateProduct(Product product) {
        Product savedProduct = productRepository.save(product);

        ProductDTO returnProductDTO = productMapper.productToProductDTO(savedProduct);
        returnProductDTO.setVendorUrl(getVendorUrl(savedProduct.getVendor().getId()));
        returnProductDTO.setCategoryUrl(getCategoryUrl(savedProduct.getCategory().getId()));

        return returnProductDTO;
    }

    private String parseUrlForListing(String url) {
        String[] parts = url.split("/");
        return parts[3];
    }
}