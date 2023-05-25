package com.fct.apprecetascocinaftc.Modelo;

public class Recetas {
    public Recetas(){

    }
    private String nombre;
    private String steps;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String ingredientes;
    private String idUsuario;
    private String categoria;



    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getIngrdientes() {
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

    public Recetas(String nombre, String steps, String ingredientes, String idUsuario, String categoria) {
        this.nombre = nombre;
        this.steps = steps;
        this.ingredientes = ingredientes;
        this.idUsuario = idUsuario;
        this.categoria = categoria;
    }
}
