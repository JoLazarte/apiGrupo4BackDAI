package com.uade.tpo.api_grupo4.controllers.courses;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.Inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseView {
    private Long id;
    private String name;
    private String content;
    private String requirements;
    private String description;
    private int length;
    private int price;
    private CourseMode mode;
    private String fechaInicio;
    private String fechaFin;
    @JsonIgnore
    private List<Headquarter> sedes;
    private List<CourseScheduleView> cronogramas;

    public Course toEntity() {
        return Course.builder()
                .id(this.id)
                .name(this.name)
                .content(this.content)
                .requirements(this.requirements)
                .description(this.description)
                .length(this.length)
                .price(this.price)
                .mode(this.mode)
                .fechaInicio(LocalDate.parse(this.fechaInicio)) // Convertimos el String a LocalDate
                .fechaFin(LocalDate.parse(this.fechaFin))       // Convertimos el String a LocalDate
                .sedes(this.sedes)
                .build();
    }
}
