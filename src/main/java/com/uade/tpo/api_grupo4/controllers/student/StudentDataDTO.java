package com.uade.tpo.api_grupo4.controllers.student;

public class StudentDataDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private double cuentaCorriente;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public double getCuentaCorriente() {
        return cuentaCorriente;
    }
    public void setCuentaCorriente(double cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }
    
    
}