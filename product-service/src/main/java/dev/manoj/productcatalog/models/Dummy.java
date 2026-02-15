package dev.manoj.productcatalog.models;


import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class Dummy extends BaseModel implements Comparable, Comparator {
    private String name;
    private String address;
    private Integer age;

    public static void main(String[] args) {
        List<Integer> l1= List.of(1, 2, 3, 4, 5);

        Integer N = l1.stream()
                .reduce(
                        0,
                        (numberOfElements, elem) -> {
                            return numberOfElements+1;
                        }

                );

        System.out.println("Number of elements in stream "+N);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
