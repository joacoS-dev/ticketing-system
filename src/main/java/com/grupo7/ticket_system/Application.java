package com.grupo7.ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import com.grupo7.ticket_system.users.UserRepository;

@SpringBootApplication
@RestController //?¿?¿?¿?¿?¿?¿?¿?¿?¿?
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*@Bean
    CommandLineRunner probar(UserRepository repo) {
        return args -> {
            int filas = repo.saveTeam(22, "NACIONAL");
            System.out.println("Filas insertadas: " + filas);
        };
    }*/
	
}