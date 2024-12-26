package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.EstacaoDAO;
import com.lambdacode.spring.boot.crud.entity.Estacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacoes")
public class EstacaoController {
    @Autowired
    private EstacaoDAO estacaoDAO;

    @GetMapping
    public List<Estacao> getAll() {
        return estacaoDAO.getAllEstacao();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacao> getById(@PathVariable int id) {
        Estacao estacao = estacaoDAO.getById(id);
        return ResponseEntity.ok().body(estacao);
    }

    @PostMapping("/add")
    public ResponseEntity<String>  save(@RequestBody Estacao estacao) {
        estacaoDAO.save(estacao);
        return ResponseEntity.ok("Estacao saved successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (estacaoDAO.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        estacaoDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Estacao> updateEstacao(@PathVariable int id, @RequestBody Estacao estacao) {
        try {
            // Verifica se o ID na URL corresponde ao da entidade no corpo
            if (id != estacao.getIdEstacao()) {
                return ResponseEntity.badRequest().build();
            }
            // Chama o serviço para atualizar a estação
            Estacao updatedEstacao = estacaoDAO.updateEstacao(estacao);
            return ResponseEntity.ok(updatedEstacao);
        } catch (RuntimeException ex) {
            // Caso a estação não seja encontrada ou ocorra outro erro
            return ResponseEntity.notFound().build();
        }

    }
}