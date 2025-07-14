package com.uade.tpo.api_grupo4.controllers.courseSchedule;

public class CreateCourseScheduleRequest {
    private String horaInicio;
    private String horaFin;
    private String instructor;
    private int vacancy;
    private int diaEnQueSeDicta;
    
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public int getVacancy() {
        return vacancy;
    }
    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }
    public int getDiaEnQueSeDicta() {
        return diaEnQueSeDicta;
    }
    public void setDiaEnQueSeDicta(int diaEnQueSeDicta) {
        this.diaEnQueSeDicta = diaEnQueSeDicta;
    }

    
}