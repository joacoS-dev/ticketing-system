package com.grupo7.ticket_system.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.grupo7.ticket_system.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    public User saveRegisteredUser(User user){

        //conseguir codigo postal
        String sqltogetpostalcodeid= "SELECT id_codigo_postal FROM codigo_postal_direccion WHERE codigo_postal = ?";
        try {
            int postalCodeId = template.queryForObject(sqltogetpostalcodeid, int.class,user.getPostalCode());
            user.setPostalCodeId(postalCodeId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Invalid postal code");
        }
        //guardar usuario
        String sqltosaveuser= "INSERT INTO usuario (mail,pais_documento,tipo_documento,numero_documento,calle_direccion,numero_direccion, id_codigo_postal) VALUES (?,?,?,?,?,?,?)";
        template.update(sqltosaveuser,user.getEmail(), user.getDocumentCountry(), user.getDocumentType(), user.getDocumentNumber(), user.getStreetAddress(), 
                        user.getNumberAddress(),user.getPostalCodeId());

        //conseguir id del usuario (autogenerado x la base de datos)
        String sqltogetuserid= "SELECT LAST_INSERT_ID()";        
        user.setUserId(template.queryForObject(sqltogetuserid, int.class));

        //guardar telefonos vinculados al usuario
        String sqltosavephones= "INSERT INTO telefono(numero_telefono, id_usuario) VALUES (?,?)";
        for(String phone: user.getPhones()){
            template.update(sqltosavephones,phone,user.getUserId());
        }

        return user;
    }

    public boolean existsByMail(String mail){
        //buscar mail en db
        String sql= "SELECT COUNT(*) FROM usuario WHERE mail= ?";
        return template.queryForObject(sql, int.class, mail) >0;
    }
}