package dev.manoj.productcatalog.models;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rating extends BaseModel{

    private Double rate;
    private Integer count;
}
