package com.uade.tpo.api_grupo4.controllers.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.controllers.Controlador;

@RestController
@RequestMapping("/apiInscripcion")
public class ApiInscripcion {
     @Autowired
    private Controlador controlador;
    
    @Autowired
    public ApiInscripcion(Controlador controlador) {
        this.controlador = controlador;
    }

    @PostMapping("/inscribir")
    public ResponseEntity<?> enrollStudent(@RequestBody InscripcionDTO inscripcionDTO) {
        try {
            // Le pasamos el DTO completo al controlador
            InscripcionExitosaDTO resultado = controlador.enrollStudent(inscripcionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al inscribir.");
        }
    }
    
    @PostMapping("/cancelar")
    public ResponseEntity<?> cancelarInscripcion(@RequestBody CancelacionDTO cancelacionDTO) {
        try {
            ResultadoCancelacionDTO resultado = controlador.cancelarInscripcion(cancelacionDTO);
            return ResponseEntity.ok(resultado);
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Captura errores de negocio (ej: "ya está cancelada", "no encontrado")
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al procesar la cancelación.");
        }
    }
    
    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<InscripcionView>> getInscriptionsByStudent(@PathVariable("studentId") Long studentId) {
        try {
            List<InscripcionView> inscriptions = controlador.findByStudent(studentId);
            return ResponseEntity.ok(inscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
