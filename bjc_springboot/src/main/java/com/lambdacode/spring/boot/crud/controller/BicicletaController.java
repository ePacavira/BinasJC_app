/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.Bicicleta;
import com.lambdacode.spring.boot.crud.service.BicicletaService;
import com.lambdacode.spring.boot.crud.service.ReservaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gate
 */
@RestController
@RequestMapping("/bicicletas")
public class BicicletaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/devolver")
    public ResponseEntity<Map<String, Object>> devolverBicicleta(
            @RequestParam Integer idReserva,
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEstacaoDevolucao) {
        try {
            // Chamar o serviço para processar a devolução
            Map<String, Object> response = reservaService.devolverBicicleta(idReserva, idUsuario, idEstacaoDevolucao);

            // Verificar sucesso no retorno do serviço
            if ((Boolean) response.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (ResponseStatusException ex) {
            // Tratar exceções específicas
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse); // Alteração aqui
        } catch (Exception ex) {
            // Tratar outras exceções genéricas
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erro interno no servidor: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/reservar")
    public ResponseEntity<Map<String, Object>> reservarBicicleta(@RequestBody Map<String, Integer> payload) {
        Integer idUsuario = payload.get("id_usuario");
        Integer idEstacao = payload.get("id_estacao");
        Integer idBicicleta = payload.get("id_bicicleta");

        Map<String, Object> response = reservaService.reservarBicicleta(idUsuario, idEstacao, idBicicleta);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Autowired
    private BicicletaService bicicletaService;

    @PostMapping("/levantar")
    public ResponseEntity<Bicicleta> levantarBicicleta(@RequestParam Integer idReserva, @RequestParam Integer idUsuario) {
        try {
           Bicicleta bicicleta =  bicicletaService.levantarBicicleta(idReserva, idUsuario);
            return ResponseEntity.ok(bicicleta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Endpoint para obter todas as bicicletas.
     */
    @GetMapping
    public ResponseEntity<List<Bicicleta>> getAllBicicletas() {
        List<Bicicleta> bicicletas = bicicletaService.getAllBicicletas();
        return ResponseEntity.ok(bicicletas);
    }

    @PostMapping("/add")
    public ResponseEntity<Bicicleta>save(@RequestBody Bicicleta bicicleta) {
        bicicletaService.salvarBicicleta(bicicleta);
        return ResponseEntity.ok(bicicleta);
    }


}
