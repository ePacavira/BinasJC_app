/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.repository.TrajetoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetoriaService {

    @Autowired
    private TrajetoriaRepository trajetoriaRepository;

    public List<Trajetoria> findAll() {
        return trajetoriaRepository.findAll();
    }

    public Optional<Trajetoria> findById(Integer id) {
        return trajetoriaRepository.findById(id);
    }

    public Trajetoria save(Trajetoria trajetoria) {
        return trajetoriaRepository.save(trajetoria);
    }

    public void deleteById(Integer id) {
        trajetoriaRepository.deleteById(id);
    }
}

