package com.springframework.springmvcrest.bootstrap;

import com.springframework.springmvcrest.domain.Category;
import com.springframework.springmvcrest.service.CategoryService;
import org.springframework.boot.CommandLineRunner;

public class Bootstrap implements CommandLineRunner {
    private CategoryService categoryService;

    public Bootstrap(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        // Will run any application code on startup
        Category fruit = new Category();
        fruit.setName("Fruit");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category dried = new Category();
        dried.setName("Dried");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryService.save(fruit);
        categoryService.save(fresh);
        categoryService.save(dried);
        categoryService.save(exotic);
        categoryService.save(nuts);

        System.out.println("Loading Categories...");
        */
    }
}