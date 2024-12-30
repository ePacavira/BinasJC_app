package com.lambdacode.spring.boot.crud.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bicicletas")
public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBicicleta;

    @ManyToOne
    @JoinColumn(name = "id_estacao", nullable = true)
    private Estacao estacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBicicleta status = StatusBicicleta.DISPONIVEL;

    public enum StatusBicicleta {
        DISPONIVEL, RESERVADA, EM_USO, MANUTENCAO;

        @JsonCreator
        public static StatusBicicleta fromString(String value) {
            if (value != null) {
                for (StatusBicicleta status : StatusBicicleta.values()) {
                    if (status.name().equalsIgnoreCase(value)) {
                        return status;
                    }
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value);
        }
    }
}
