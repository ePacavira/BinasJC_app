package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "trajectorias")
@NoArgsConstructor
@AllArgsConstructor
public class Trajectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_trajectory;

    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private long startTime;
    private long endTime;

    //Relacionamento com utilizador
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

}
