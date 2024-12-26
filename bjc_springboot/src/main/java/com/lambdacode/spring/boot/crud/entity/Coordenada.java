package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordenada {
    private double latitude;
    private double longitude;

    public Coordenada() {}

    public Coordenada(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
