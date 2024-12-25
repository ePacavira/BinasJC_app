package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Estacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EstacaoRepository  extends CrudRepository<Estacao, Integer> {
    @Override
    List<Estacao> findAll();
}
