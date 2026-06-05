package com.grupo7.ticket_system.users;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.User;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user){
        if(userRepository.existsByMail(user.getEmail()) == false){
        return userRepository.saveRegisteredUser(user);
        }else{
            throw new IllegalArgumentException("Email alredy used");
        }
    }
}