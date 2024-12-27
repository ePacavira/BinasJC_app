package com.lambdacode.spring.boot.crud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "estacoes")
public class Estacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstacao;
    private String nome;
    private double latitude;
    private double longitude;
    private int totalBike;

    //Relacionamento com Bike
    @OneToMany(mappedBy = "estacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Bike> bikes = new ArrayList<>();

    public void adicionarBike(Bike bike) {
        bikes.add(bike);
        totalBike++;
    }

    public int getTotalBike() {
        return totalBike;
    }

    public void setTotalBike(int totalBike) {
        this.totalBike = totalBike;
    }
}
