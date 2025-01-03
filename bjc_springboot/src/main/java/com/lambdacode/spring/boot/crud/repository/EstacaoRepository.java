/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gate
 */
@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Integer> {
}

