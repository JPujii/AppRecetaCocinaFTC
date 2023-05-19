package com.fct.apprecetascocinaftc.Modelo;

public class Recetas {
    public Recetas(){

    }
    private String titulo;
    private int steps;
    private String imagen;
    private String idUsuario;
    private String categoria;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recetas(String titulo, int steps, String imagen, String idUsuario, String categoria) {
        this.titulo = titulo;
        this.steps = steps;
        this.imagen = imagen;
        this.idUsuario = idUsuario;
        this.categoria = categoria;
    }
}
