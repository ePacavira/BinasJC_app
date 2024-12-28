/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.entity;

import com.lambdacode.spring.boot.crud.entity.Bicicleta.StatusBicicleta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_bicicleta", nullable = false)
    private Bicicleta bicicleta;

    @ManyToOne
    @JoinColumn(name = "id_estacao_retirada", nullable = false)
    private Estacao estacaoRetirada;

    @ManyToOne
    @JoinColumn(name = "id_estacao_devolucao", nullable = true)
    private Estacao estacaoDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBicicleta status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime horarioReserva = LocalDateTime.now();

    private LocalDateTime horarioRetirada;
    private LocalDateTime horarioDevolucao;
}
