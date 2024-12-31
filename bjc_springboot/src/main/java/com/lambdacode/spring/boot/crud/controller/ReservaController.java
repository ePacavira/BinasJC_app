package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.ReservaDAO;
import com.lambdacode.spring.boot.crud.entity.Reservas;
import com.lambdacode.spring.boot.crud.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    ReservaService reservaService;

    @GetMapping
    public ResponseEntity<?> getAllReservas() {
        try {
            return ResponseEntity.ok(reservaService.getAllReservas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
