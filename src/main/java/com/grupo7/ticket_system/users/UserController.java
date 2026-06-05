package com.grupo7.ticket_system.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.ticket_system.models.User;


@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public User registerUser(@RequestBody User user) { //@requestbody mapea las key del json a los atributos de la clase.
        return userService.registerUser(user);
    }
}