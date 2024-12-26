package com.lambdacode.spring.boot.crud;

import com.lambdacode.spring.boot.crud.DAO.BikeDAO;
import com.lambdacode.spring.boot.crud.DAO.EstacaoDAO;
import com.lambdacode.spring.boot.crud.DAO.TrajectoryDAO;
import com.lambdacode.spring.boot.crud.entity.*;
import com.lambdacode.spring.boot.crud.repository.EstacaoRepository;
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@SpringBootTest
class SpringBootCrudApplicationTests {
    @Autowired
    private TrajectoryDAO trajectoryDAO;

    @Autowired
    UserRepository userRepository;

    @Test
    void TrajectoryTest() {

        User user = userRepository.findByEmail("eunice@gmail.com")
                .orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));


        Trajectory trajectory = new Trajectory();
        trajectory.setStationEnd("EstacaoProxima1");
        trajectory.setStationEnd("EstacaoProxima2");
        trajectory.setDistance(234.09f);
        trajectory.setUser(user);

        trajectoryDAO.save(trajectory);

    }

   /* @Autowired
    private BikeDAO bikeDAO;

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void BikeTest() {
        // Obtenha uma estacao válida
        Estacao estacao = estacaoRepository.findById(1).orElseThrow(() -> new RuntimeException("Estacao not found"));

        Bike bike = new Bike();
        bike.setEstacao(estacao); // Definindo a estacao válida

        bikeDAO.save(bike);
    }*/
}

