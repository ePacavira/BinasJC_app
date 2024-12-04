package com.lambdacode.spring.boot.crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    private String email;
    private String palavra_passe; // Nome mais claro para senha
    private Integer pontuacao; // Mantido para lógica futura de pontuação
}
