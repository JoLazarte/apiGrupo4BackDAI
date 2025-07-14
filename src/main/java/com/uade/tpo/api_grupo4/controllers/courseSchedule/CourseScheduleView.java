package com.uade.tpo.api_grupo4.controllers.courseSchedule;

import com.uade.tpo.api_grupo4.entity.Headquarter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleView {

    private Long id;
    private String horaInicio;
    private String horaFin;
    private String instructor;
    private int vacancy;
    private int diaEnQueSeDicta;
    private Headquarter sede;

}
