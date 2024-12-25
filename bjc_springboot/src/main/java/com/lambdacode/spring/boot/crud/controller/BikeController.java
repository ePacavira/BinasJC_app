package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.BikeDAO;
import com.lambdacode.spring.boot.crud.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    @Autowired
    private BikeDAO bikeDAO;

    // Endpoint para salvar uma bicicleta
    @PostMapping
    public ResponseEntity<String> saveBike(@RequestBody Bike bike) {
        bikeDAO.save(bike);
        return ResponseEntity.ok("Bike saved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable int id) {
        Bike bike = bikeDAO.getById(id);
        return ResponseEntity.ok(bike);
    }

    /*@GetMapping
    public List<Bike> getBikes() {
        return bikeDAO.getAll();
    }*/

    @GetMapping
    public ResponseEntity<List<Bike>> getAllBikes() {
        List<Bike> bikes = bikeDAO.getAll();
        return ResponseEntity.ok(bikes);
    }

    // Endpoint para deletar uma bicicleta pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBikeById(@PathVariable int id) {
        try {
            bikeDAO.deleteById(id);
            return ResponseEntity.ok("Bike deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para reservar uma bicicleta
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBike(@RequestParam Long userId, @RequestParam Long bikeId) {
        try {
            bikeDAO.reserveBike(userId, bikeId);
            return ResponseEntity.ok("Bike reserved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
