package com.example.BinasJc.repository;

import com.example.BinasJc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    // Consulta personalizada para buscar por nome de usuário insensível ao caso
    @Query("SELECT u FROM User u WHERE LOWER(u.userName) = LOWER(:userName)")
    User findByUserNameIgnoreCase(@Param("userName") String userName);

    // Método existente para buscar por email
    User findByEmail(String email);
}
