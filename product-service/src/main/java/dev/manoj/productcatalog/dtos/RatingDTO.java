package dev.manoj.productcatalog.dtos;


import dev.manoj.productcatalog.models.Rating;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RatingDTO implements Serializable   {
    private Double rate;
    private Integer count;

    public Rating toRating(){
        return Rating
                .builder()
                .rate(rate)
                .count(count)
                .build();
    }
}
