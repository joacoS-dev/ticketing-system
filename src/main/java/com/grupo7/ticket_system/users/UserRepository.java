package com.grupo7.ticket_system.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    /*public int saveTeam(int id, String nombre_equipo){

        String sql= "INSERT INTO equipo(id_equipo, nombre_equipo) VALUES(?,?)";
        return template.update(sql,1,"Real Madrid");
    }*/

    public void saveUser(String mail, String pais_documento, String tipo_documento, String numero_documento, String calle_direccion, String numero_direccion, int id_codigo_postal){
        //guardar usuario en la base de datos
    }

    public void findUserByMail(String mail){
        //buscar usuario con ese mail en db
    }
}