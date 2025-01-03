/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.repository.TrajetoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;

@Service
public class TrajetoriaService {

    @Autowired
    private TrajetoriaRepository trajetoriaRepository;

    public List<Trajetoria> findAll() {
        return trajetoriaRepository.findAll();
    }

    @Transactional
    public Trajetoria findById(Integer id) {
        Trajetoria trajetoria = trajetoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trajetoria não encontrada"));
        Hibernate.initialize(trajetoria.getPontosIntermediarios()); // Inicializa os pontos intermediários dentro da transação
        return trajetoria;
    }

    /*public Optional<Trajetoria> findById(Integer id) {
        Optional<Trajetoria> trajetoria = trajetoriaRepository.findById(id);
        trajetoria.ifPresent(t -> Hibernate.initialize(t.getPontosIntermediarios()));
        return trajetoria;
    }*/
    public Trajetoria save(Trajetoria trajetoria) {
        return trajetoriaRepository.save(trajetoria);
    }

    public void deleteById(Integer id) {
        trajetoriaRepository.deleteById(id);
    }
}
