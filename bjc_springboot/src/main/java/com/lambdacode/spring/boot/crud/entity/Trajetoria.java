package com.lambdacode.spring.boot.crud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Reserva reserva; // Referência à reserva

    @Column(name = "latitude_inicio", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitudeInicio;

    @Column(name = "longitude_inicio", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitudeInicio;

    @Column(name = "latitude_fim", precision = 10, scale = 7)
    private BigDecimal latitudeFim;

    @Column(name = "longitude_fim", precision = 10, scale = 7)
    private BigDecimal longitudeFim;

    @Column(name = "horario_inicio", nullable = false)
    private LocalDateTime horarioInicio;

    @Column(name = "horario_fim")
    private LocalDateTime horarioFim;

    @JsonManagedReference // Indica que esta relação será serializada
    @OneToMany(mappedBy = "trajetoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PontoIntermediario> pontosIntermediarios = new ArrayList<>();

    @PrePersist
    private void setHorarioInicio() {
        if (horarioInicio == null) {
            horarioInicio = reserva.getHorarioReserva(); // Usa o horário da reserva para preencher o horário de início
        }
    }
}
