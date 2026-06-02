package com.grupo7.ticket_system.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;
    
    public void registerUser(String mail, String pais_documento, String tipo_documento, String numero_documento, String calle_direccion, String numero_direccion, int id_codigo_postal){
        
    }
}