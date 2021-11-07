package com.springframework.springmvcrest.repository;

import com.springframework.springmvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName (String categoryName);
}