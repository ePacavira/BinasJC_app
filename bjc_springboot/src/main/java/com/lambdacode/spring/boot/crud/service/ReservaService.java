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
import java.util.HashMap;
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

        // Verificar se a bicicleta existe
        Bicicleta bicicleta = bicicletaRepository.findById(idBicicleta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bicicleta não encontrada"));

        // Verificar se a bicicleta está disponível na estação
        if (!bicicleta.getStatus().equals(Bicicleta.StatusBicicleta.DISPONIVEL) ||
            !bicicleta.getEstacao().getIdEstacao().equals(idEstacao)) {
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
        reserva.setStatus(StatusBicicleta.RESERVADA);

        reservaRepository.save(reserva);

        // Atualizar status da bicicleta
        bicicleta.setStatus(Bicicleta.StatusBicicleta.RESERVADA);
        bicicletaRepository.save(bicicleta);

        response.put("success", true);
        response.put("message", "Bicicleta reservada com sucesso!");
        response.put("reserva", reserva);
        return response;
    }
}
