package com.uade.tpo.api_grupo4.controllers.courses;

public class ResultadoCancelacionDTO {
    private String mensaje;
    private double montoReintegrado;
    private String nuevoEstadoInscripcion;

    public ResultadoCancelacionDTO(String mensaje, double montoReintegrado, String nuevoEstadoInscripcion) {
        this.mensaje = mensaje;
        this.montoReintegrado = montoReintegrado;
        this.nuevoEstadoInscripcion = nuevoEstadoInscripcion;
    }

    // Getters
    public String getMensaje() {
        return mensaje;
    }

    public double getMontoReintegrado() {
        return montoReintegrado;
    }

    public String getNuevoEstadoInscripcion() {
        return nuevoEstadoInscripcion;
    }
    
    // Setters
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setMontoReintegrado(double montoReintegrado) {
        this.montoReintegrado = montoReintegrado;
    }

    public void setNuevoEstadoInscripcion(String nuevoEstadoInscripcion) {
        this.nuevoEstadoInscripcion = nuevoEstadoInscripcion;
    }
}