package com.lambdacode.spring.boot.crud.controller;

import com.lambdacode.spring.boot.crud.DAO.TrajectoryDAO;
import com.lambdacode.spring.boot.crud.entity.Trajectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trajectories")
public class TrajectoryController {
    @Autowired
    private TrajectoryDAO trajectoryDAO;

    @PostMapping("/add")
    public ResponseEntity<String> save(@RequestBody Trajectory trajectory) {
        trajectoryDAO.save(trajectory);
        return ResponseEntity.ok("Trajectory added successfully");
    }
    @GetMapping
    public List<Trajectory> getAll() {
        return trajectoryDAO.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestBody Trajectory trajectory) {
        trajectoryDAO.delete(trajectory);
        return ResponseEntity.ok("Trajectory deleted successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Trajectory trajectory, @PathVariable int id) {
        try{
            if(id != trajectory.getId_trajectory()){
                return ResponseEntity.badRequest().build();
            }
            Trajectory trajectory1 = trajectoryDAO.update(id);
            return ResponseEntity.ok(trajectory1.toString());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}