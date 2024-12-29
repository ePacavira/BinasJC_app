/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pontos_intermediarios")
public class PontoIntermediario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPonto;

    @ManyToOne
    @JoinColumn(name = "id_trajetoria", nullable = false)
    private Trajetoria trajetoria;

    @Column(nullable = false, precision = 10, scale = 8)
    private Double latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private Double longitude;
}

