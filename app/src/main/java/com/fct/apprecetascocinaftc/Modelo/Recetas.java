package com.fct.apprecetascocinaftc.Modelo;

public class Recetas {
    public Recetas(){

    }
    private String nombre;
    private String pasos;
    private String ingredientes;
    private String userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Recetas(String nombre, String pasos, String ingredientes, String userID, String categoria) {
        this.nombre = nombre;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
        this.userID = userID;
        this.categoria = categoria;
    }
}
