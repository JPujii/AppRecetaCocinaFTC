package com.fct.apprecetascocinaftc.Modelo;

public class Recipe {
    public int id;
    public String titulo;
    public int steps;
    public String imagen;
    public int idUsuario;

    public Recipe(int id, String titulo, int steps, String imagen, int idUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.steps = steps;
        this.imagen = imagen;
        this.idUsuario = idUsuario;
    }
}
