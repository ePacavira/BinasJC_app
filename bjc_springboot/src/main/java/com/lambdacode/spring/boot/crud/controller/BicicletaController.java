/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.service.BicicletaService;
import com.lambdacode.spring.boot.crud.service.ReservaService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gate
 */
@RestController
@RequestMapping("/bicicletas")
public class BicicletaController {

    @Autowired
    private ReservaService reservaService;

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
    public ResponseEntity<?> levantarBicicleta(@RequestParam Long idReserva, @RequestParam Integer idUsuario) {
        try {
            bicicletaService.levantarBicicleta(idReserva, idUsuario);
            return ResponseEntity.ok("Bicicleta levantada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
