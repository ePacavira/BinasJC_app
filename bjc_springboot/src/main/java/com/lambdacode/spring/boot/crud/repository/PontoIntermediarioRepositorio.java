package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoIntermediarioRepositorio extends JpaRepository<PontoIntermediario, Integer> {
}
