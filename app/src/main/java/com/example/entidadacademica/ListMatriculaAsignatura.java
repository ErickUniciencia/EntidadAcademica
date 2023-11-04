package com.example.entidadacademica;

public class ListMatriculaAsignatura {
    private int id;
    private int idEstudiante;
    private int idAsignatura;
    private int idDocente;

    private String nombreEstudiante;
    private String nombreAsignatura;
    private String nombreDocente;

    public ListMatriculaAsignatura(int id, int idEstudiante, int idAsignatura, int idDocente) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idAsignatura = idAsignatura;
        this.idDocente = idDocente;
    }

    public int getId() {
        return id;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }
}
