package com.lambdacode.spring.boot.crud.DAO;

import com.lambdacode.spring.boot.crud.entity.Reservas;
import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.repository.ReservaRepository;
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaDAO {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;


    public void save(Reservas reserva) {
        reservaRepository.save(reserva);
    }
    public List<Reservas> findAll() {
        List<Reservas>reservas = new ArrayList<>();
        reservaRepository.findAll().forEach(reservas::add);
        return reservas;
    }
    public Reservas findById(int id) {
        return reservaRepository.findById(id).get();
    }
    public Reservas findReservaByUserId(int userId) {
        Optional<Reservas> reservaOptional = reservaRepository.findById(userId);
        if (reservaOptional.isPresent()) {
            return reservaOptional.get();
        } else {
            throw new RuntimeException("Reserva não encontrada para o ID do usuário: " + userId);
        }
    }

}


