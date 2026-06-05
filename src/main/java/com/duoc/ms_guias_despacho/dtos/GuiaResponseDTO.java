package com.duoc.ms_guias_despacho.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GuiaResponseDTO {

    private Long id;

    private String numeroGuia;

    private String transportista;

    private String destinatario;

    private LocalDate fecha;

    private String estado;
}