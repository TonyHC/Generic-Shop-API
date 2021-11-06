package com.springframework.springmvcrest.repository;

import com.springframework.springmvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v from Vendor v JOIN FETCH v.products WHERE v.id = ?1")
    Optional<Vendor> findVendorProductsById(Long id);
}