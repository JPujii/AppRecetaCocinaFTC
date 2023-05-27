package com.fct.apprecetascocinaftc.Modelo;

public class Recetas {
    public Recetas(){

    }
    private String nombre;
    private String pasos;
    private String ingredientes;
    private String idUsuario;
    private String categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
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

    public Recetas(String nombre, String pasos, String ingredientes, String idUsuario, String categoria) {
        this.nombre = nombre;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
        this.idUsuario = idUsuario;
        this.categoria = categoria;
    }
}
