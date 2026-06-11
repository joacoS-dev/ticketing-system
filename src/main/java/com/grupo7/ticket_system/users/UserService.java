package com.grupo7.ticket_system.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.User;
import com.grupo7.ticket_system.models.User;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    public User registerUser(User user){
        if(!userRepository.existsByMail(user.getEmail())){
            String hashed= encoder.encode(user.getPassword());
            user.setPassword(hashed);
            return userRepository.saveUser(user);
        }else{
            throw new IllegalArgumentException("Email alredy used");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }
}