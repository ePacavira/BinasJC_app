package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Reserva;
import com.lambdacode.spring.boot.crud.entity.Bicicleta.StatusBicicleta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Optional<Reserva> findByUsuario_IdUsuarioAndStatus(Integer idUsuario, StatusBicicleta status);
    boolean existsByUsuario_IdUsuarioAndStatusIn(Integer idUsuario, List<StatusBicicleta> status);
}

