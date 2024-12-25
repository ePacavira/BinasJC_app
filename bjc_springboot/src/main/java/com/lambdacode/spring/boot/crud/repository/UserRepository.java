package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}
