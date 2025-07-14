package com.uade.tpo.api_grupo4.controllers.courses;

public class InscripcionDTO {
    private Long idAlumno;
    private Long idCronograma;
    private MetodoDePago metodoDePago;

    // Getters y Setters
    public Long getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }
    public Long getIdCronograma() {
        return idCronograma;
    }
    public void setIdCronograma(Long idCronograma) {
        this.idCronograma = idCronograma;
    }
    public MetodoDePago getMetodoDePago() {
        return metodoDePago;
    }
    public void setMetodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
}