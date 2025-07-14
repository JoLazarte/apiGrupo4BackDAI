package com.uade.tpo.api_grupo4.controllers.courseAttend;

public class AsistenciaResultadoDTO {
    private int totalClases;
    private long clasesAsistidas;
    private double porcentajeAsistencia;
    private boolean aprobado;
    private String mensaje;

    // Constructor, Getters y Setters
    public AsistenciaResultadoDTO(int totalClases, long clasesAsistidas, double porcentajeAsistencia, boolean aprobado, String mensaje) {
        this.totalClases = totalClases;
        this.clasesAsistidas = clasesAsistidas;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.aprobado = aprobado;
        this.mensaje = mensaje;
    }

    public int getTotalClases() {
        return totalClases;
    }

    public void setTotalClases(int totalClases) {
        this.totalClases = totalClases;
    }

    public long getClasesAsistidas() {
        return clasesAsistidas;
    }

    public void setClasesAsistidas(long clasesAsistidas) {
        this.clasesAsistidas = clasesAsistidas;
    }

    public double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(double porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
}