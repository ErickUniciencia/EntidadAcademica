package com.example.entidadacademica;

public class Asignatura {
    private int id;
    private String nombre;

    public Asignatura(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
