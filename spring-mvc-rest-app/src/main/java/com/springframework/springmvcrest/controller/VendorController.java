package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.*;
import com.springframework.springmvcrest.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Vendor", description = "Vendor API")
@RestController
@RequestMapping(VendorController.VENDOR_BASE_URL)
public class VendorController {
    public static final String VENDOR_BASE_URL = "/api/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Operation(summary = "List all vendors")
    @ApiResponse(responseCode = "200", description = "Found vendors",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = VendorListDTO.class))))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @Operation(summary = "Get vendor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found vendor",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VendorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = @Content)})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@Parameter(description = "Vendor id", required = true) @PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @Operation(summary = "Get vendor product(s) by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found vendor products",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = VendorListProductsDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vendor products not found", content = @Content)})
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public VendorListProductsDTO getVendorProductsById(@Parameter(description = "Vendor id", required = true) @PathVariable Long id) {
        return vendorService.getVendorProductsById(id);
    }

    @Operation(summary = "Create new vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new vendor",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VendorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @Operation(summary = "Create new vendor product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new vendor product",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VendorProductDTO.class))),
            @ApiResponse(responseCode = "409", description = "Invalid request", content = @Content)})
    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorProductDTO createNewVendorProduct(@Parameter(description = "Vendor id", required = true) @PathVariable Long id,
                                                   @Valid @RequestBody VendorProductDTO vendorProductDTO) {
        return vendorService.createNewVendorProduct(id, vendorProductDTO);
    }

    @Operation(summary = "Update existing vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated vendor",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VendorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vendor not found", content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@Parameter(description = "Vendor id", required = true) @PathVariable Long id,
                                  @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @Operation(summary = "Patch existing vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patched vendor",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VendorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vendor not found", content = @Content)})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@Parameter(description = "Vendor id", required = true) @PathVariable Long id,
                                 @Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @Operation(summary = "Delete existing vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted vendor", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vendor not found", content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@Parameter(description = "Vendor id", required = true) @PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}