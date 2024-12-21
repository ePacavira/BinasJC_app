package com.lambdacode.spring.boot.crud.repository;
import com.lambdacode.spring.boot.crud.entity.Trajectory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrajectoryRepository extends JpaRepository<Trajectory, Integer> {
    //Recuperar trajetoria por usuario
    List<Trajectory> findByTrajectoryIdUser(Integer trajectoryId);
}
