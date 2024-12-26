package com.lambdacode.spring.boot.crud.DAO;

import com.lambdacode.spring.boot.crud.entity.Bike;
import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.repository.BikeRepository;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeDAO {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EstacaoRepository estacaoRepository;
    @Autowired
    private EstacaoDAO estacaoDAO;

    public void save(Bike bike) {
        Estacao estacao = estacaoDAO.getById(bike.getEstacao().getIdEstacao());
        if (estacao == null) {
            throw new RuntimeException("Estacao not found");
        }else{
            bike.setUser(null);
            bikeRepository.save(bike);

        }
    }

    public Bike getById (int id){
        return bikeRepository.findById(id).get();
    }

    public List<Bike> getAll(){
        List<Bike> bikes = new ArrayList<Bike>();
        bikeRepository.findAll().forEach(bikes::add);
        return bikes;
    }

    public void deleteById (int id){
        if(bikeRepository.existsById(id)){
            bikeRepository.deleteById(id);
        }else{
            throw new RuntimeException("Bike not found");
        }
    }

    // Exemplo de método no serviço para reservar uma bike
    public void reserveBike(Long userId, Long bikeId) {
        //verificar se o ID do user e da bicicleta são válidos

    }

}
