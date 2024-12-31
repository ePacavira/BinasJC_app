package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacaoService {
    @Autowired
    EstacaoRepository estacaoRepository;
    public List<Estacao> findAll() {
        return estacaoRepository.findAll();
    }

    public Optional<Estacao> findById(Integer id) {
        return estacaoRepository.findById(id);
    }

    public Estacao save(Estacao estacao) {
        return estacaoRepository.save(estacao);
    }

    public void deleteById(Integer id) {
        estacaoRepository.deleteById(id);
    }
}
