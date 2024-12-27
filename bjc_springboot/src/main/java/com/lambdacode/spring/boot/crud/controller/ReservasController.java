package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.ReservaDAO;
import com.lambdacode.spring.boot.crud.entity.Reservas;
import com.lambdacode.spring.boot.crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    private ReservaDAO reservaDAO;

    @PostMapping("/add")
    public ResponseEntity<String> saveReserva(@RequestBody Reservas reserva) {
        try {
            reservaDAO.save(reserva);
            return ResponseEntity.ok("Reserva salva com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservas>> getAllReservas() {
        List<Reservas> reservas = reservaDAO.findAll();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservas> getReservaById(@PathVariable int id) {
        Reservas reserva = reservaDAO.findById(id);
        if (reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.status(404).body(reserva);
        }
    }

    // Endpoint para buscar reservas pelo ID do usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<Reservas> getReservasByUserId(@PathVariable int userId) {
        Reservas reserva = null;
        try {
            reserva = reservaDAO.findReservaByUserId(userId);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(reserva);
        }
    }

}
