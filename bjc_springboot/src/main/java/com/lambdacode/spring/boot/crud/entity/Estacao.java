package com.lambdacode.spring.boot.crud.entity;

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

    //Relacionamento com Bike
    @OneToMany(mappedBy = "estacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bike> bikes = new ArrayList<>();
}
