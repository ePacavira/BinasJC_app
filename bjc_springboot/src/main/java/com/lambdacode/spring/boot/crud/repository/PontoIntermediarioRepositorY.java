package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.PontoIntermediario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoIntermediarioRepositorY extends JpaRepository<PontoIntermediario, Integer> {
}
