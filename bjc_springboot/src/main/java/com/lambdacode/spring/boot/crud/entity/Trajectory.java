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
    private Integer id_trajectory;
    private String stationStart;
    private String stationEnd;

    //Relacionamento com utilizador


}
