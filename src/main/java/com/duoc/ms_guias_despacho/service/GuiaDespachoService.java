package com.duoc.ms_guias_despacho.service;

import com.duoc.ms_guias_despacho.dtos.GuiaRequestDTO;
import com.duoc.ms_guias_despacho.dtos.GuiaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface GuiaDespachoService {

    GuiaResponseDTO crearGuia(GuiaRequestDTO request);

    List<GuiaResponseDTO> listarGuias();

    List<GuiaResponseDTO> buscarPorTransportista(String transportista);

    List<GuiaResponseDTO> buscarPorFecha(LocalDate fecha);

    void eliminarGuia(Long id);

    GuiaResponseDTO actualizarGuia(Long id, GuiaRequestDTO request);
}
