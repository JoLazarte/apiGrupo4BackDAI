package com.uade.tpo.api_grupo4.controllers.courses;

import java.time.LocalDateTime;

public class InscripcionExitosaDTO {

    private Long idInscripcion;
    private String nombreCurso;
    private String nombreEstudiante;
    private String emailEstudiante;
    private LocalDateTime fechaInscripcion;
    private String estado;

    // --- Constructor ---
    public InscripcionExitosaDTO(Long idInscripcion, String nombreCurso, String nombreEstudiante, String emailEstudiante, LocalDateTime fechaInscripcion, String estado) {
        this.idInscripcion = idInscripcion;
        this.nombreCurso = nombreCurso;
        this.nombreEstudiante = nombreEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    // --- Getters y Setters ---
    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getEmailEstudiante() {
        return emailEstudiante;
    }

    public void setEmailEstudiante(String emailEstudiante) {
        this.emailEstudiante = emailEstudiante;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
