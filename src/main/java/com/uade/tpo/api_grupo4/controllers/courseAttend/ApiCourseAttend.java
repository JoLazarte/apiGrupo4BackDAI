package com.uade.tpo.api_grupo4.controllers.courseAttend;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.uade.tpo.api_grupo4.controllers.Controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/apiCourseAttend")
@RequiredArgsConstructor
public class ApiCourseAttend {

    private final Controlador controlador;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
        try {
            // Llamamos a un método en el controlador que crearemos ahora
            controlador.registrarAsistencia(asistenciaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Asistencia registrada con éxito.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/inscripcion/{id}/resultado")
    public ResponseEntity<?> getResultadoAsistencia(@PathVariable Long id) {
        try {
            AsistenciaResultadoDTO resultado = controlador.verificarAsistencia(id);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/qr/{cronogramaId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Long cronogramaId) {
        try {
            String qrContent = String.valueOf(cronogramaId);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 250, 250);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            return ResponseEntity.ok(pngData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}