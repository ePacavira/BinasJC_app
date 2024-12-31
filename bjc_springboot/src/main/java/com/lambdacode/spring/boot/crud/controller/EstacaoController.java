package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.service.EstacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estacoes")
public class EstacaoController {
    @Autowired
    private EstacaoService estacaoService;

    @GetMapping
    public List<Estacao> getEstacoes() {
        return estacaoService.findAll();
    }
    @PostMapping("/add")
    public ResponseEntity<Estacao> addEstacao(@RequestBody Estacao estacao) {
        estacaoService.save(estacao);
        return ResponseEntity.ok(estacao);
    }
}
