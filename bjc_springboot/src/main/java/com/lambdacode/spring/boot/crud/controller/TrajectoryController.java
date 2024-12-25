package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.TrajectoryDAO;
import com.lambdacode.spring.boot.crud.entity.Trajectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trajectories")
public class TrajectoryController {
    @Autowired
    private TrajectoryDAO trajectoryDAO;

    @PostMapping("/add")
    public void save(@RequestBody Trajectory trajectory) {
        trajectoryDAO.save(trajectory);
    }
    @GetMapping
    public List<Trajectory> getAll() {
        return trajectoryDAO.getAllTrajectory();
    }
}
