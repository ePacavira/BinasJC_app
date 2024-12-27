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

    @PostMapping("/add/{idEstacao}")
    public ResponseEntity<String> saveBike(@RequestBody Bike bike, @PathVariable int idEstacao) {
        try {
            bikeDAO.save(bike,idEstacao);
            return ResponseEntity.ok("Bike saved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable int id) {
        Bike bike = bikeDAO.getById(id);
        return ResponseEntity.ok(bike);
    }

    @GetMapping
    public List<Bike> getBikes() {
        return bikeDAO.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBikeById(@PathVariable int id) {
        try {
            bikeDAO.deleteById(id);
            return ResponseEntity.ok("Bike deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBike(@RequestParam int userId, @RequestParam int bikeId, @RequestParam int idEstacao) {
        try {
            bikeDAO.reservarBike(userId, bikeId,idEstacao);
            return ResponseEntity.ok("Bike reserved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/devolver")
    public ResponseEntity<String> devolverBike(@RequestParam int userId, @RequestParam int idBike, @RequestParam int idEstacao) {
        try {
            bikeDAO.DevolverBike(userId, idBike, idEstacao);
            return ResponseEntity.ok("Bike returned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



}
