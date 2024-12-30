package com.lambdacode.spring.boot.crud.DAO;

/*import com.lambdacode.spring.boot.crud.entity.Bike;
import com.lambdacode.spring.boot.crud.entity.Trajectory;*/
import com.lambdacode.spring.boot.crud.entity.User;
/*import com.lambdacode.spring.boot.crud.repository.BikeRepository;
import com.lambdacode.spring.boot.crud.repository.TrajectoryRepository;*/
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {
    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private TrajectoryRepository trajectoryRepository;

    @Autowired
    BikeRepository bikeRepository;*/

    public User findUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    /*public void addTrajectoryToUser(int userId, Trajectory trajectory) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        trajectory.setUser(user);
        user.getTrajectories().add(trajectory);
        userRepository.save(user);
    }*/

}
