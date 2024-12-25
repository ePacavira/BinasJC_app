package com.lambdacode.spring.boot.crud.DAO;

import com.lambdacode.spring.boot.crud.entity.Trajectory;
import com.lambdacode.spring.boot.crud.repository.TrajectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrajectoryDAO {

    @Autowired
    private TrajectoryRepository repository;

    public  void save(Trajectory trajectory){
        repository.save(trajectory);
    }
    public  void delete(Trajectory trajectory){
        repository.delete(trajectory);
    }
    public List<Trajectory> getAllTrajectory(){
        List<Trajectory>trajectories = new ArrayList<Trajectory>();
        Streamable.of(repository.findAll()).forEach(trajectories ::add);
        return trajectories;
    }
    /*@PostMapping("/trajectories/save")
    public ResponseEntity<Trajectory> save(@RequestBody Trajectory trajectory) {
        Trajectory savedTrajectory = repository.save(trajectory);
        return ResponseEntity.ok(savedTrajectory);
    }*/

}
