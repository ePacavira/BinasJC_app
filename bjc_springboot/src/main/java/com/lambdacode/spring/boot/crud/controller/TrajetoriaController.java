/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.service.ResourceNotFoundException;
import com.lambdacode.spring.boot.crud.service.TrajetoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trajetorias")
public class TrajetoriaController {

    @Autowired
    private TrajetoriaService trajetoriaService;

    @GetMapping
    public List<Trajetoria> getAllTrajetorias() {
        return trajetoriaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trajetoria> getTrajetoriaById(@PathVariable Integer id) {
        try {
            Trajetoria trajetoria = trajetoriaService.findById(id);
            return ResponseEntity.ok(trajetoria);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public Trajetoria createTrajetoria(@RequestBody Trajetoria trajetoria) {
        return trajetoriaService.save(trajetoria);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Trajetoria> updateTrajetoria(@PathVariable Integer id, @RequestBody Trajetoria trajetoria) {
        return trajetoriaService.findById(id)
                .map(existingTrajetoria -> {
                    trajetoria.setIdTrajetoria(id); // Garante que o ID permaneça o mesmo
                    return ResponseEntity.ok(trajetoriaService.save(trajetoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajetoria(@PathVariable Integer id) {
        try {
            trajetoriaService.findById(id); // Apenas para validar a existência
            trajetoriaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
