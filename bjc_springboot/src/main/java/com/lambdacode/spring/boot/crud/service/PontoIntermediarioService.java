package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.repository.PontoIntermediarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PontoIntermediarioService {
    PontoIntermediarioRepositorio pontoIntermediarioRepositorio;
    public List<PontoIntermediario> findAll() {
        return pontoIntermediarioRepositorio.findAll();
    }

    public Optional<PontoIntermediario> findById(Integer id) {
        return pontoIntermediarioRepositorio.findById(id);
    }

    public PontoIntermediario save(PontoIntermediario pontoIntermediario) {
        return pontoIntermediarioRepositorio.save(pontoIntermediario);
    }

    public void deleteById(Integer id) {
        pontoIntermediarioRepositorio.deleteById(id);
    }

}
