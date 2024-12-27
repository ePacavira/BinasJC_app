package com.lambdacode.spring.boot.crud.DAO;

import com.lambdacode.spring.boot.crud.entity.Bike;
import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.repository.BikeRepository;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import com.lambdacode.spring.boot.crud.repository.ReservaRepository;
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

    @Autowired
    ReservaRepository reservaRepository;

    public void save(Bike bike , int idEstacao) {
        Estacao estacao = estacaoRepository.findById(idEstacao).get();
        if (estacao == null) {
            throw new RuntimeException("Estacao not found");
        }else{
            bike.setEstacao(estacao);
            bike.setStatus(Bike.Status.valueOf("DISPONIVEL"));
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

    public void reservarBike(int userId, int idBike, int idEstacao){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Estacao estacao = estacaoRepository.findById(idEstacao).get();

        BikeDAO bikeDAO = new BikeDAO();
        Bike bike = bikeRepository.findById(idBike)
                .orElseThrow(() -> new RuntimeException("Bike not found"));
        if (bike != null && user != null && estacao != null && estacao.getTotalBike() >= 1) {
            bike.setStatus(Bike.Status.valueOf("RESERVADA"));
            bike.setIdUser(userId);
            //diminuir o total de bikes Disponiveis
            estacao.setTotalBike(estacao.getTotalBike() - 1);
            //adicionar a tabela de Reservas


        }

    }

    public void DevolverBike(int userId, int idBike, int idEstacao){
        //aprimorar a função de modo a verificar onde a bicicleta está sendo devolvida
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BikeDAO bikeDAO = new BikeDAO();
        Estacao estacao = estacaoRepository.findById(idEstacao).get();

        Bike bike = bikeRepository.findById(idBike)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        if (bike != null && bike.getIdUser() == userId){
            bike.setIdUser(0);
            //adicionar o total de bikes Disponiveis
            estacao.setTotalBike(estacao.getTotalBike() + 1);
            //retirar da tabela de resevas
        }
    }

}
