package com.lambdacode.spring.boot.crud.repository;
import com.lambdacode.spring.boot.crud.entity.Trajectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrajectoryRepository extends CrudRepository<Trajectory, Integer> {

}
