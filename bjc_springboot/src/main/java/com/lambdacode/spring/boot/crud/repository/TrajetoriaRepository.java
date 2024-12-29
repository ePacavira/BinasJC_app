/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Trajetoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetoriaRepository extends JpaRepository<Trajetoria, Integer> {
        
}
