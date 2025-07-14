package com.uade.tpo.api_grupo4.controllers.student;

import java.time.LocalDateTime;

public class PaymentHistoryDTO {
    private Long id; // ID de la inscripción
    private LocalDateTime date;
    private String receipt;
    private String courseName;
    private double amount;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getReceipt() {
        return receipt;
    }
    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}