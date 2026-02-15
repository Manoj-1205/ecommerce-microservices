package dev.manoj.productcatalog.dtos;

import lombok.Data;

@Data
public class GetProductRequestDto {
    private String query;
    private int noOfResults;
    private int offset;
}
