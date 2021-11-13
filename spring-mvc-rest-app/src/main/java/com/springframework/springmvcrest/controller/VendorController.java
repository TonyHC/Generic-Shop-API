package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.VendorDTO;
import com.springframework.springmvcrest.api.model.VendorListDTO;
import com.springframework.springmvcrest.api.model.VendorListProductsDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.service.VendorService;
import io.swagger.annotations.*;
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
    @ApiResponse(code = 200, message = "Found vendors",
            response = VendorListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "Get vendor by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "Get vendor product(s) by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found vendor products",
                response = VendorListProductsDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor products not found") })
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public VendorListProductsDTO getVendorProductsById(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id) {
        return vendorService.getVendorProductsById(id);
    }

    @ApiOperation(value = "Create new vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created new vendor"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Create new vendor product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created new vendor product"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorProductDTO createNewVendorProduct(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id,
                                                   @Valid @RequestBody VendorProductDTO vendorProductDTO) {
        return vendorService.createNewVendorProduct(id, vendorProductDTO);
    }

    @ApiOperation(value = "Update existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 409, message = "Vendor not found") })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id,
                                  @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "Patch existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patched vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 409, message = "Vendor not found") })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id,
                                 @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete existing vendor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted vendor"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Vendor not found") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@ApiParam(value = "Vendor id", required = true) @PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}