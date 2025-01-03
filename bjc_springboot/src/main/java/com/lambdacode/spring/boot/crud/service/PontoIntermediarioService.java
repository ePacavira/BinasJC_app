package com.lambdacode.spring.boot.crud.service;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import com.lambdacode.spring.boot.crud.repository.PontoIntermediarioRepositorY;
import com.lambdacode.spring.boot.crud.repository.TrajetoriaRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.lambdacode.spring.boot.crud.repository.PontoIntermediarioRepositorY;

@Service
public class PontoIntermediarioService {

    @Autowired
    private PontoIntermediarioRepositorY pontoIntermediarioRepositorio;

    @Autowired
    private TrajetoriaRepository trajetoriaRepositorio;
    // Obter todos os pontos intermediários
    public List<PontoIntermediario> getAllPontosIntermediarios() {
        List<PontoIntermediario> pontos = pontoIntermediarioRepositorio.findAll();
        // Inicializa a trajetória para evitar problemas com FetchType.LAZY
        pontos.forEach(ponto -> Hibernate.initialize(ponto.getTrajetoria()));
        return pontos;
    }

    // Obter um ponto intermediário por ID
    public Optional<PontoIntermediario> getPontoIntermediarioById(Integer id) {
        return pontoIntermediarioRepositorio.findById(id).map(ponto -> {
            Hibernate.initialize(ponto.getTrajetoria()); // Inicializa a trajetória para o ponto específico
            return ponto;
        });
    }

    // Criar ou atualizar um ponto intermediário
    public PontoIntermediario createPontoIntermediario(PontoIntermediario pontoIntermediario) {
        if (pontoIntermediario.getTrajetoria() == null) {
            throw new IllegalArgumentException("Trajetória não pode ser nula.");
        }
        return pontoIntermediarioRepositorio.save(pontoIntermediario);
    }

    // Atualizar um ponto intermediário existente
    public Optional<PontoIntermediario> updatePontoIntermediario(Integer id, PontoIntermediario pontoIntermediario) {
        return pontoIntermediarioRepositorio.findById(id).map(existingPonto -> {
            existingPonto.setTrajetoria(pontoIntermediario.getTrajetoria());
            existingPonto.setLatitude(pontoIntermediario.getLatitude());
            existingPonto.setLongitude(pontoIntermediario.getLongitude());
            return pontoIntermediarioRepositorio.save(existingPonto);
        });
    }

    // Deletar um ponto intermediário por ID
    public boolean deletePontoIntermediario(Integer id) {
        if (pontoIntermediarioRepositorio.existsById(id)) {
            pontoIntermediarioRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    //salvar vários pontos intermediários
    public List<PontoIntermediario> addPontosToTrajetoria(Integer trajetoriaId, List<PontoIntermediario> pontos) {
        // Buscar a trajetória no banco
        Trajetoria trajetoria = trajetoriaRepositorio.findById(trajetoriaId)
                .orElseThrow(() -> new RuntimeException("Trajetória não encontrada"));

        // Associar a trajetória a cada ponto intermediário
        for (PontoIntermediario ponto : pontos) {
            ponto.setTrajetoria(trajetoria);
        }

        // Salvar todos os pontos no banco
        return pontoIntermediarioRepositorio.saveAll(pontos);
    }
}
