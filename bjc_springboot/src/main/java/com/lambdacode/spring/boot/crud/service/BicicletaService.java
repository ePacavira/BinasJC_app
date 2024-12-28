/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.Bicicleta;
import com.lambdacode.spring.boot.crud.entity.Bicicleta.StatusBicicleta;
import com.lambdacode.spring.boot.crud.entity.Reserva;
import com.lambdacode.spring.boot.crud.repository.BicicletaRepository;
import com.lambdacode.spring.boot.crud.repository.ReservaRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gate
 */
@Service
public class BicicletaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private BicicletaRepository bicicletaRepository;

    public void levantarBicicleta(Long idReserva, Integer idUsuario) {
        // Buscar a reserva
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        // Verificar se o usuário que está levantando é o mesmo que fez a reserva
        if (!reserva.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new IllegalStateException("Usuário não autorizado para levantar esta bicicleta.");
        }

        // Verificar se o status da reserva permite retirada
        if (!reserva.getStatus().equals(Bicicleta.StatusBicicleta.RESERVADA)) {
            throw new IllegalStateException("A reserva não está no status correto para retirada.");
        }

        // Atualizar status da reserva e bicicleta
        reserva.setHorarioRetirada(LocalDateTime.now());
        reserva.setStatus(Bicicleta.StatusBicicleta.EM_USO);

        Bicicleta bicicleta = reserva.getBicicleta();
        bicicleta.setStatus(Bicicleta.StatusBicicleta.EM_USO);

        reservaRepository.save(reserva);
        bicicletaRepository.save(bicicleta);
    }

}
