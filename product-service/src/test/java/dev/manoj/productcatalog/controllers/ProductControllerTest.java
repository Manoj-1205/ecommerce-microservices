package dev.manoj.productcatalog.controllers;

import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.services.ProductService;
import jdk.jfr.Name;

import org.assertj.core.api.Assert.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @InjectMocks
    private StubProductService stubProductService;
    @Test
    public void testAddNewProductShouldReturnProduct(){

        ProductDto productDto = ProductDto.builder()
                .title("Baggy Pants")
                .price(1200.00)
                .categoryId(2L)
                .build();
        Product product = stubProductService.addNewProduct(productDto);

        assertThat(product).isNotNull();
        assertThat(product.getTitle()).isEqualTo("Baggy Pants");

    }

}