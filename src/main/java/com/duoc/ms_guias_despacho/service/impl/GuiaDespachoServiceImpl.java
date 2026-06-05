package com.duoc.ms_guias_despacho.service.impl;

import com.duoc.ms_guias_despacho.dtos.GuiaRequestDTO;
import com.duoc.ms_guias_despacho.dtos.GuiaResponseDTO;
import com.duoc.ms_guias_despacho.entity.GuiaDespacho;
import com.duoc.ms_guias_despacho.exception.ResourceNotFoundException;
import com.duoc.ms_guias_despacho.repository.GuiaDespachoRepository;
import com.duoc.ms_guias_despacho.service.GuiaDespachoService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuiaDespachoServiceImpl implements GuiaDespachoService {

    private final GuiaDespachoRepository repository;

    @Override
    public GuiaResponseDTO crearGuia(GuiaRequestDTO request) {

        GuiaDespacho guia = new GuiaDespacho();

        guia.setNumeroGuia(UUID.randomUUID().toString());

        guia.setTransportista(request.getTransportista());

        guia.setDestinatario(request.getDestinatario());

        guia.setFecha(LocalDate.now());

        guia.setEstado("GENERADA");

        GuiaDespacho guiaGuardada = repository.save(guia);

        return convertirDTO(guiaGuardada);
    }

    @Override
    public List<GuiaResponseDTO> listarGuias() {

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    public List<GuiaResponseDTO> buscarPorTransportista(String transportista) {

        return repository.findByTransportista(transportista)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    public List<GuiaResponseDTO> buscarPorFecha(LocalDate fecha) {

        return repository.findByFecha(fecha)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    public void eliminarGuia(Long id) {

        repository.deleteById(id);
    }

    @Override
    public GuiaResponseDTO actualizarGuia(Long id, GuiaRequestDTO request) {

        GuiaDespacho guia = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrada"));

        guia.setTransportista(request.getTransportista());

        guia.setDestinatario(request.getDestinatario());

        GuiaDespacho guiaActualizada = repository.save(guia);

        return convertirDTO(guiaActualizada);
    }

    private GuiaResponseDTO convertirDTO(GuiaDespacho guia) {

        return GuiaResponseDTO.builder()
                .id(guia.getId())
                .numeroGuia(guia.getNumeroGuia())
                .transportista(guia.getTransportista())
                .destinatario(guia.getDestinatario())
                .fecha(guia.getFecha())
                .estado(guia.getEstado())
                .build();
    }
}