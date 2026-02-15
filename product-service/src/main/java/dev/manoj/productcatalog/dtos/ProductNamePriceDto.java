package dev.manoj.productcatalog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductNamePriceDto {
    private String productName;
    private Double productPrice;
}
