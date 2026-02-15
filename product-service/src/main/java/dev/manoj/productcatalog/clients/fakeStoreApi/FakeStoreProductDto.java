package dev.manoj.productcatalog.clients.fakeStoreApi;

import dev.manoj.productcatalog.dtos.RatingDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class FakeStoreProductDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private RatingDTO rating;


}
