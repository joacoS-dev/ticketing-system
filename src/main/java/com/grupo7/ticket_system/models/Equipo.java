package com.grupo7.ticket_system.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Equipo {
    
    public int id_equipo;
    public String nombre_equipo;

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    @Override
    public String toString() {
        return "Equipo [id_equipo=" + id_equipo + ", nombre_equipo=" + nombre_equipo + "]";
    }
}
