package com.uade.tpo.api_grupo4.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseAttended {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inscripcion_id")
    private Inscripcion inscripcion;

    @Column(nullable = false)
    private LocalDate fechaAsistencia;

    @Column(nullable = false)
    private Boolean presente;
}