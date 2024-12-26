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
    private TrajectoryRepository trajectorieRepository;

    public  void save(Trajectory trajectory){
        trajectorieRepository.save(trajectory);
    }

    public Trajectory getById(int id){
        return trajectorieRepository.findById(id).get();
    }
    public List<Trajectory> getAll(){
        List<Trajectory>trajectories = new ArrayList<>();
        trajectorieRepository.findAll().forEach(trajectories::add);
        return trajectories;
    }
    public  void delete(Trajectory trajectory){
        trajectorieRepository.delete(trajectory);
    }
    public  void deleteById(int id){
        if(trajectorieRepository.existsById(id)){
            trajectorieRepository.deleteById(id);
        }else{
            throw new RuntimeException("trajectoria not found");
        }
    }

    public Trajectory update(int id){
        if(trajectorieRepository.existsById(id)){
            return trajectorieRepository.save(getById(id));
        }else{
            throw new RuntimeException("trajectoria not found");
        }
    }


}
