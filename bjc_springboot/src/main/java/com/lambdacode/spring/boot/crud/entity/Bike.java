package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBike;

    @ManyToOne
    @JoinColumn(name = "idEstacao", nullable = false)
    private Estacao estacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private User user;


}
