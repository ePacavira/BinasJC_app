package com.lambdacode.spring.boot.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "bikes")
public class Bike {

    public enum Status {
        DISPONIVEL,
        RESERVADA,
        EM_USO,
        MANUTENCAO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBike;

    @ManyToOne
    @JoinColumn(name = "id_estacao", nullable = false)
    @JsonBackReference
    private Estacao estacao;


    private int idUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Bike() {
    }


    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public Integer getIdBike() {
        return idBike;
    }

    public void setIdBike(Integer idBike) {
        this.idBike = idBike;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
