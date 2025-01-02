package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import com.lambdacode.spring.boot.crud.service.PontoIntermediarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos-intermediarios")
public class PontoIntermediarioController {

    @Autowired
    private PontoIntermediarioService pontoIntermediarioService;

    // 1. Obter todos os pontos intermediários
    @GetMapping
    public ResponseEntity<List<PontoIntermediario>> getAllPontosIntermediarios() {
        List<PontoIntermediario> pontos = pontoIntermediarioService.getAllPontosIntermediarios();
        return ResponseEntity.ok(pontos);
    }

    // 2. Obter um ponto intermediário por ID
    @GetMapping("/get/{id}")
    public ResponseEntity<PontoIntermediario> getPontoIntermediarioById(@PathVariable Integer id) {
        return pontoIntermediarioService.getPontoIntermediarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Criar um novo ponto intermediário
    @PostMapping("/add")
    public ResponseEntity<PontoIntermediario> createPontoIntermediario(@RequestBody PontoIntermediario pontoIntermediario) {
        PontoIntermediario novoPonto = pontoIntermediarioService.createPontoIntermediario(pontoIntermediario);
        return ResponseEntity.ok(novoPonto);
    }

    // 4. Atualizar um ponto intermediário existente
    @PutMapping("/update/{id}")
    public ResponseEntity<PontoIntermediario> updatePontoIntermediario(@PathVariable Integer id, @RequestBody PontoIntermediario pontoIntermediario) {
        return pontoIntermediarioService.updatePontoIntermediario(id, pontoIntermediario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Deletar um ponto intermediário
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePontoIntermediario(@PathVariable Integer id) {
        if (pontoIntermediarioService.deletePontoIntermediario(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
