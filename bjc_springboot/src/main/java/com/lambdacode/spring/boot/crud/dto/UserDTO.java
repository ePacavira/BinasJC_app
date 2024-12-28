package com.lambdacode.spring.boot.crud.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
    private Integer idUsuario; // Identificador único do usuário
    private String nome;       // Nome do usuário
    private String email;      // E-mail do usuário
    private Integer pontuacao; // Pontuação do usuário
    private List<Integer> bicicletasIds; // Lista de IDs das bicicletas associadas ao usuário
}
