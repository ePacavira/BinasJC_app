package com.lambdacode.spring.boot.crud.repository;

import com.lambdacode.spring.boot.crud.entity.Bike;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<Bike, Integer> {

}
