package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.service.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Vendor", description = "Vendor API")
@RestController
@RequestMapping(VendorController.VENDOR_BASE_URL)
public class VendorController {
    public static final String VENDOR_BASE_URL = "/api/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "List all vendors")
    @ApiResponse(code = 200, message = "Successfully retrieved vendors",
            response = VendorListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "Get vendor by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "Get vendor product(s) by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vendor products",
                response = VendorListProductsDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor product(s) not found") })
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public VendorListProductsDTO getVendorProductsById(@PathVariable Long id) {
        return vendorService.getVendorProductsById(id);
    }

    @ApiOperation(value = "Create new vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new vendor"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@Valid  @RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Create new vendor product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new vendor product"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorProductDTO createNewVendorProduct(@PathVariable Long id,
                                                   @Valid @RequestBody VendorProductDTO vendorProductDTO) {
        return vendorService.createNewVendorProduct(id, vendorProductDTO);
    }

    @ApiOperation(value = "Update existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "Patch existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}