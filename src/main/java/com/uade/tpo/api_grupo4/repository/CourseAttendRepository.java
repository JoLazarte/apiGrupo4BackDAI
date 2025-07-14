package com.uade.tpo.api_grupo4.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.Inscripcion;



@Repository
public interface CourseAttendRepository extends JpaRepository<CourseAttended, Long> {
    boolean existsByInscripcionAndFechaAsistencia(Inscripcion inscripcion, LocalDate fecha);
    long countByInscripcion(Inscripcion inscripcion);

}