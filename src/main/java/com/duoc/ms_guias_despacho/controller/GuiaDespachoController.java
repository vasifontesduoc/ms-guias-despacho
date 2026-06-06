package com.duoc.ms_guias_despacho.controller;

import com.duoc.ms_guias_despacho.dtos.GuiaRequestDTO;
import com.duoc.ms_guias_despacho.dtos.GuiaResponseDTO;
import com.duoc.ms_guias_despacho.service.GuiaDespachoService;
import com.duoc.ms_guias_despacho.service.S3Service;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guias")
@RequiredArgsConstructor
public class GuiaDespachoController {

    private final GuiaDespachoService service;
    private final S3Service s3Service;

    @Operation(summary = "Crear una nueva guía de despacho")
    @PostMapping
    public GuiaResponseDTO crearGuia(
            @Valid @RequestBody GuiaRequestDTO request) {

        return service.crearGuia(request);
    }

    @Operation(summary = "Listar todas las guías")
    @GetMapping
    public List<GuiaResponseDTO> listarGuias() {

        return service.listarGuias();
    }

    @Operation(summary = "Buscar guías por transportista")
    @GetMapping("/transportista/{transportista}")
    public List<GuiaResponseDTO> buscarPorTransportista(
            @PathVariable String transportista) {

        return service.buscarPorTransportista(transportista);
    }

    @Operation(summary = "Buscar guías por fecha")
    @GetMapping("/fecha")
    public List<GuiaResponseDTO> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return service.buscarPorFecha(fecha);
    }

    @Operation(summary = "Actualizar una guía")
    @PutMapping("/{id}")
    public GuiaResponseDTO actualizarGuia(
            @PathVariable Long id,
            @Valid @RequestBody GuiaRequestDTO request) {

        return service.actualizarGuia(id, request);
    }

    @Operation(summary = "Descargar archivo desde S3")
    @GetMapping("/download/{nombre}")
    public ResponseEntity<byte[]> descargarArchivo(
            @PathVariable String nombre) {

        byte[] archivo = s3Service.descargarArchivo(nombre);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + nombre)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(archivo);
    }

    @Operation(summary = "Listar archivos del bucket S3")
    @GetMapping("/archivos")
    public List<String> listarArchivos() {

        return s3Service.listarArchivos();
    }

    @Operation(summary = "Eliminar una guía")
    @DeleteMapping("/{id}")
    public void eliminarGuia(@PathVariable Long id) {

        service.eliminarGuia(id);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String subirArchivo(
            @RequestParam("archivo") MultipartFile archivo) {

        return s3Service.subirArchivo(archivo);
    }
}