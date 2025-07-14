package com.uade.tpo.api_grupo4.controllers.courses;

public class CancelacionDTO {
    private Long idInscripcion;
    private TipoReintegro tipoReintegro;

    // Getters
    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public TipoReintegro getTipoReintegro() {
        return tipoReintegro;
    }

    // Setters
    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setTipoReintegro(TipoReintegro tipoReintegro) {
        this.tipoReintegro = tipoReintegro;
    }
}