package dev.manoj.productcatalog.dtos;

import lombok.*;
import jakarta.annotation.Nullable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private Long categoryId;
    private RatingDTO rating;
//    private String spec;
    @Nullable
    private Boolean isDeleted;

}
