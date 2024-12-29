/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trajetorias")
public class Trajetoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrajetoria;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_bicicleta", nullable = false)
    private Bicicleta bicicleta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @Column(nullable = false, precision = 10, scale = 8)
    private Double latitudeInicio;

    @Column(nullable = false, precision = 11, scale = 8)
    private Double longitudeInicio;

    @Column(precision = 10, scale = 8)
    private Double latitudeFim;

    @Column(precision = 11, scale = 8)
    private Double longitudeFim;

    @Column(nullable = false)
    private LocalDateTime horarioInicio;

    private LocalDateTime horarioFim;

    @ElementCollection
    @CollectionTable(name = "pontos_intermediarios", joinColumns = @JoinColumn(name = "id_trajetoria"))
    @Column(name = "ponto_intermediario")
    private List<String> pontosIntermediarios; // Lista de coordenadas no formato "lat,lon"
}
