package com.fct.apprecetascocinaftc.Modelo;

import java.util.Date;

public class Usuario {
    public int id;
    public String nombre;
    public String pass;
    public String email;

    public Usuario(int id, String nombre, String pass, String email) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.email = email;
    }
}
