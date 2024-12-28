/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Bicicleta;
import com.lambdacode.spring.boot.crud.entity.Reserva;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gate
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Optional<Reserva> findByUsuario_IdUsuarioAndStatus(Integer idUsuario, Bicicleta.StatusBicicleta status);
}
