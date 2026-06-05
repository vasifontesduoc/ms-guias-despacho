package com.duoc.ms_guias_despacho.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "GUIA_DESPACHO")
@Data
public class GuiaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String numeroGuia;

    private String transportista;

    private String destinatario;

    private LocalDate fecha;

    private String estado;

    private String rutaS3;
}