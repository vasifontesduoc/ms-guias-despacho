package com.duoc.ms_guias_despacho.repository;

import com.duoc.ms_guias_despacho.entity.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Long> {

    List<GuiaDespacho> findByTransportista(String transportista);

    List<GuiaDespacho> findByFecha(LocalDate fecha);
}