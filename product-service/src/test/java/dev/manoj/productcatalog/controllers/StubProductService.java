package dev.manoj.productcatalog.controllers;

import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Product;

public class StubProductService {
    public Product addNewProduct(ProductDto productDto) {


        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .build();
    }
}
