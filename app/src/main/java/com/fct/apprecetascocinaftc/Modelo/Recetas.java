package com.fct.apprecetascocinaftc.Modelo;

public class Recetas {
    public Recetas(){

    }
    public String titulo;
    public int steps;
    public String imagen;
    public String idUsuario;
    public String categoria;

    public int id;
    public Recetas(String titulo, int steps, String imagen, String idUsuario, String categoria) {
        this.titulo = titulo;
        this.steps = steps;
        this.imagen = imagen;
        this.idUsuario = idUsuario;
        this.categoria = categoria;
    }
}
