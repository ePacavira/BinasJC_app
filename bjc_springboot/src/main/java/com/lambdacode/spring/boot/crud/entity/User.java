package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id_usuario;
    private String nome;
    @Column(unique = true)
    private String email;
    private String palavra_passe;
    private Integer pontuacao ;

    //Relacionamento com TrajectoryEntity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trajectory> trajectories = new ArrayList<>();

    public void adicionarTrajectory(Trajectory trajectory) {
        trajectories.add(trajectory);
    }

}
