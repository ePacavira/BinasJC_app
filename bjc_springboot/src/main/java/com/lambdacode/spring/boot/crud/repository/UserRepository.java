package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
