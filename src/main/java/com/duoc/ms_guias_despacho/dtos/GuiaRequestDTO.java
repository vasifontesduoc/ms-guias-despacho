package com.duoc.ms_guias_despacho.dtos;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GuiaRequestDTO {

    @NotBlank(message = "El transportista es obligatorio")
    private String transportista;

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;
}