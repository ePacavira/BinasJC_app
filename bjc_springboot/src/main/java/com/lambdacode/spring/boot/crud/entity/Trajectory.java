package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private int id_trajectory;

    @Column(nullable = false)
    private int idBike;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @Column(nullable = false)
    private double latitudeStart;

    @Column(nullable = false)
    private double latitudeEnd;

    @Column(nullable = false)
    private double longitudeStart;

    @Column(nullable = false)
    private double longitudeEnd;

    private String stationStart;
    private String stationEnd;

    private float distance;

    private LocalDate trajectoryDate;
    private LocalTime trajectoryTimeStart;
    private LocalTime trajectoryTimeEnd;


    @ElementCollection
    @CollectionTable(name = "trajectory_points", joinColumns = @JoinColumn(name = "trajectory_id"))
    private List<Coordenada> intermediatePoints;

}
