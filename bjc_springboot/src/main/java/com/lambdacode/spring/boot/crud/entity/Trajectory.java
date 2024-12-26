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

    private String stationStart;
    private String stationEnd;

    private float distance;

    private LocalDate trajectoryDate;
    private LocalTime trajectoryTimeStart;
    private LocalTime trajectoryTimeEnd;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "trajectory_points", joinColumns = @JoinColumn(name = "trajectory_id"))
    private List<Coordenada> intermediatePoints;

    @Override
    public String toString() {
        return "Trajectory{" +
                "stationStart='" + stationStart + '\'' +
                ", stationEnd='" + stationEnd + '\'' +
                ", trajectoryDate=" + trajectoryDate +
                ", trajectoryTimeStart=" + trajectoryTimeStart +
                ", trajectoryTimeEnd=" + trajectoryTimeEnd +
                ", user=" + user +
                '}';
    }
}
