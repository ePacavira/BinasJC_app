/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.*;
import com.lambdacode.spring.boot.crud.entity.Bicicleta.StatusBicicleta;
import static com.lambdacode.spring.boot.crud.entity.Bicicleta.StatusBicicleta.RESERVADA;
import com.lambdacode.spring.boot.crud.repository.BicicletaRepository;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import com.lambdacode.spring.boot.crud.repository.ReservaRepository;
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gate
 */
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> reservarBicicleta(Integer idUsuario, Integer idEstacao, Integer idBicicleta) {
        Map<String, Object> response = new HashMap<>();

        // Verificar se o usuário existe
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Verificar se o usuário já possui uma reserva ativa
        boolean hasActiveReservation = reservaRepository.existsByUsuario_IdUsuarioAndStatusIn(
                idUsuario, List.of(Bicicleta.StatusBicicleta.RESERVADA, Bicicleta.StatusBicicleta.EM_USO)
        );

        if (hasActiveReservation) {
            response.put("success", false);
            response.put("message", "Usuário já possui uma reserva ativa.");
            return response;
        }

        // Verificar se a bicicleta existe
        Bicicleta bicicleta = bicicletaRepository.findById(idBicicleta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bicicleta não encontrada"));

        // Verificar se a bicicleta está disponível na estação
        if (!bicicleta.getStatus().equals(Bicicleta.StatusBicicleta.DISPONIVEL)
                || !bicicleta.getEstacao().getIdEstacao().equals(idEstacao)) {
            response.put("success", false);
            response.put("message", "Bicicleta não disponível ou não encontrada na estação especificada.");
            return response;
        }

        // Verificar se a estação existe
        Estacao estacao = estacaoRepository.findById(idEstacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estação não encontrada"));

        // Criar nova reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(user);
        reserva.setBicicleta(bicicleta);
        reserva.setEstacaoRetirada(estacao);
        reserva.setStatus(Bicicleta.StatusBicicleta.RESERVADA);

        reservaRepository.save(reserva);

        // Atualizar status da bicicleta
        bicicleta.setStatus(Bicicleta.StatusBicicleta.RESERVADA);
        bicicletaRepository.save(bicicleta);

        response.put("success", true);
        response.put("message", "Bicicleta reservada com sucesso!");
        response.put("reserva", reserva);
        return response;
    }

    public Map<String, Object> devolverBicicleta(Long idReserva, Integer idUsuario, Integer idEstacaoDevolucao) {
        Map<String, Object> response = new HashMap<>();

        // Encontrar a reserva pelo ID
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        // Verificar se o usuário que está tentando devolver é o mesmo da reserva
        if (!reserva.getUsuario().getIdUsuario().equals(idUsuario)) {
            response.put("success", false);
            response.put("message", "Este usuário não pode devolver essa bicicleta.");
            return response;
        }

        // Verificar se a reserva está em uso (garantindo que a bicicleta ainda está sendo usada)
        if (!reserva.getStatus().equals(StatusBicicleta.EM_USO)) {
            response.put("success", false);
            response.put("message", "A bicicleta não está em uso.");
            return response;
        }

        // Encontrar a estação de devolução
        Estacao estacaoDevolucao = estacaoRepository.findById(idEstacaoDevolucao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estação de devolução não encontrada"));

        // Atualizar o status da bicicleta para DISPONIVEL
        Bicicleta bicicleta = reserva.getBicicleta();
        bicicleta.setStatus(StatusBicicleta.DISPONIVEL);
        bicicletaRepository.save(bicicleta);

        // Atualizar a reserva com a estação de devolução e data/hora
        reserva.setEstacaoDevolucao(estacaoDevolucao);
        reserva.setStatus(StatusBicicleta.DISPONIVEL);
        reserva.setHorarioDevolucao(LocalDateTime.now());
        reservaRepository.save(reserva);

        response.put("success", true);
        response.put("message", "Bicicleta devolvida com sucesso!");
        response.put("reserva", reserva);
        return response;
    }

}
