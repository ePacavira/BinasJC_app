package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Reservas;
import org.springframework.data.repository.CrudRepository;

public interface ReservaRepository extends CrudRepository<Reservas,Integer> {
}
