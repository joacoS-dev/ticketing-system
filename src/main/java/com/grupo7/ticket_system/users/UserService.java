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

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

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

    public List<Map<String, Object>> getMyTickets() {
        int userId = getLoggedUserId();
        return userRepository.findTicketsByUserId(userId);
    }

    public List<Map<String, Object>> getMySales() {
        int userId = getLoggedUserId();
        return userRepository.findSalesByUserId(userId);
    }

    public List<Map<String, Object>> getMyTransfers() {
        int userId = getLoggedUserId();
        return userRepository.findTransfersByUserId(userId);
    }

    public List<Map<String, Object>> getTopBuyers() {
        return userRepository.findTopBuyers();
    }

    private int getLoggedUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getUserIdByUsername(username);
    }
}