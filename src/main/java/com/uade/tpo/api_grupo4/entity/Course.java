package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String requirements;
    private String description;
    private int length;
    private int price;
    private CourseMode mode;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @ManyToMany
    @JoinTable(
    name = "headquarter_curso", 
    joinColumns = @JoinColumn(name = "course"), 
    inverseJoinColumns = @JoinColumn(name = "headquarter_id"))
    private List<Headquarter> sedes;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference
    private List<CourseSchedule> cronogramas = new ArrayList<>();

    public CourseView toView(){
        List<CourseScheduleView> cronogramasView = this.cronogramas
                .stream()
                .map(CourseSchedule::toView)
                .collect(Collectors.toList());
        return new CourseView(
            this.id,
            this.name,
            this.content,
            this.requirements,
            this.description,
            this.length,
            this.price,
            this.mode,
            this.fechaInicio.toString(),
            this.fechaFin.toString(),
            this.sedes,
            cronogramasView
        );
    }
}