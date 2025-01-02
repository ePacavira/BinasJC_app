package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import com.lambdacode.spring.boot.crud.repository.PontoIntermediarioRepositorio;
import com.lambdacode.spring.boot.crud.service.PontoIntermediarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pontoIntermediario")
public class PontoIntermediarioController {

    @Autowired
    private PontoIntermediarioService pontoIntermediarioService;

    @Autowired
    private PontoIntermediarioRepositorio pontoIntermediarioRepositorio;

    @GetMapping
    public List<PontoIntermediario> getAll() {
        return pontoIntermediarioRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoIntermediario> getPontoIntermediarioById(@PathVariable Integer id) {
        Optional<PontoIntermediario> pontoIntermediario = pontoIntermediarioService.findById(id);
        if (pontoIntermediario.isPresent()) {
            return ResponseEntity.ok(pontoIntermediario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PontoIntermediario> createOrUpdatePontoIntermediario(@RequestBody PontoIntermediario pontoIntermediario) {
        PontoIntermediario savedPontoIntermediario = pontoIntermediarioService.save(pontoIntermediario);
        return ResponseEntity.ok(savedPontoIntermediario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoIntermediario(@PathVariable Integer id) {
        pontoIntermediarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


