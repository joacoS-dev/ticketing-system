package com.grupo7.ticket_system.users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User registerUser(User user){
        normalizeAndValidateUser(user);

        if(!userRepository.existsByMail(user.getEmail())){
            String hashed= encoder.encode(user.getPassword());
            user.setPassword(hashed);
            return userRepository.saveUser(user);
        }else{
            throw new IllegalArgumentException("El email ya esta en uso");
        }
    }

    private void normalizeAndValidateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Los datos del usuario son obligatorios");
        }

        user.setUsername(requiredTrimmed(user.getUsername(), "Usuario"));
        user.setPassword(requiredTrimmed(user.getPassword(), "Contrasena"));
        user.setEmail(requiredTrimmed(user.getEmail(), "Email"));
        user.setDocumentCountry(requiredTrimmed(user.getDocumentCountry(), "Pais documento"));
        user.setDocumentType(requiredTrimmed(user.getDocumentType(), "Tipo documento"));
        user.setDocumentNumber(requiredTrimmed(user.getDocumentNumber(), "Numero documento"));
        user.setStreetAddress(requiredTrimmed(user.getStreetAddress(), "Calle"));
        user.setNumberAddress(requiredTrimmed(user.getNumberAddress(), "Numero de puerta"));
        user.setPostalCode(requiredTrimmed(user.getPostalCode(), "Codigo postal"));
    }

    private String requiredTrimmed(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " es obligatorio");
        }
        return value.trim();
    }

    public List<String> getPostalCodes() {
        return userRepository.findAllPostalCodes();
    }

    public String getRoleByUsername(String username) {
        return userRepository.getRoleByUsername(username);
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
