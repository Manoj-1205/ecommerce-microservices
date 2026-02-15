package dev.manoj.productcatalog.services;

import lombok.Getter;

@Getter
public class ServiceDemo {
    public static int counter=0;
    public int multiple(int m, int n){
        counter+=1;
        return m*n;
    }
}
