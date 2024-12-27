package com.lambdacode.spring.boot.crud.DAO;

import com.lambdacode.spring.boot.crud.entity.Bike;
import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.repository.BikeRepository;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstacaoDAO {
    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private BikeRepository bikeRepository;

    public void save(Estacao estacao) {
        estacaoRepository.save(estacao);
    }

    public Estacao getById(int id) {
       return estacaoRepository.findById(id).orElse(null);
    }

    /*public Estacao getByNome(String nome) {

    }*/
    public void delete(Estacao estacao) {
        estacaoRepository.delete(estacao);
    }
    public void deleteById(int id) {
        if (estacaoRepository.existsById(id)){
            estacaoRepository.deleteById(id);
        }else{
            throw new RuntimeException("Estação não encontrada para o ID: " + id);
        }

    }
    public List<Estacao> getAllEstacao() {
        List<Estacao> estacoes = new ArrayList<Estacao>();
        Streamable.of(estacaoRepository.findAll()).forEach(estacoes ::add);
        return estacoes;

    }
    public Estacao updateEstacao(Estacao estacao) {
        // Verifica se a estação existe
        if (estacaoRepository.existsById(estacao.getIdEstacao())) {
            return estacaoRepository.save(estacao);
        } else {
            throw new RuntimeException("Estação não encontrada para o ID: " + estacao.getIdEstacao());
        }
    }

    public void adicionarBike(int idBike, int idEstacao) {
        // Verifica se a bike existe
        if (!bikeRepository.existsById(idBike)) {
            throw new RuntimeException("Bike com ID " + idBike + " não encontrada.");
        }

        // Obtém a bike e a estação
        Bike bike = bikeRepository.findById(idBike).orElseThrow(() ->
                new RuntimeException("Bike com ID " + idBike + " não encontrada.")
        );
        Estacao estacao = estacaoRepository.findById(idEstacao).orElseThrow(() ->
                new RuntimeException("Estação com ID " + idEstacao + " não encontrada.")
        );

        estacao.adicionarBike(bike);
        estacaoRepository.save(estacao);
    }


}
