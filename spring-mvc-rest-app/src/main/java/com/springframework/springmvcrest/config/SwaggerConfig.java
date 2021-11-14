package com.springframework.springmvcrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    public static final String CATEGORY_TAG = "Category";
    public static final String CUSTOMER_TAG = "Customer";
    public static final String PRODUCT_TAG = "Product";
    public static final String VENDOR_TAG = "Vendor";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .tags(new Tag(CATEGORY_TAG, "Category API"),
                        new Tag(CUSTOMER_TAG,"Customer API"),
                        new Tag(PRODUCT_TAG, "Product API"),
                        new Tag(VENDOR_TAG, "Vendor API"));
    }

    private ApiInfo metadata() {
        Contact contact = new Contact("Tony Chao", "https://github.com/TonyHC", "tonycsends@hotmail.com");

        return new ApiInfo(
                "Generic Shop",
                "Sample REST API Practice",
                "1.0",
                "Term of Service: Free to Use",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}