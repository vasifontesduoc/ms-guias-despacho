package com.duoc.ms_guias_despacho.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class S3Service {

    private final String rutaEfs = "/app/efs/";

    public String subirArchivo(MultipartFile archivo) {

        try {

            Path rutaArchivo = Paths.get(rutaEfs + archivo.getOriginalFilename());

            Files.write(rutaArchivo, archivo.getBytes());

            return "Archivo guardado en EFS correctamente";

        } catch (Exception e) {

            throw new RuntimeException("Error al guardar archivo");
        }
    }

    public byte[] descargarArchivo(String nombreArchivo) {

        try {

            Path rutaArchivo = Paths.get(rutaEfs + nombreArchivo);

            return Files.readAllBytes(rutaArchivo);

        } catch (Exception e) {

            throw new RuntimeException("Error al descargar archivo");
        }
    }

    public List<String> listarArchivos() {

        File carpeta = new File(rutaEfs);

        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return List.of();
        }

        return java.util.Arrays.stream(archivos)
                .map(File::getName)
                .toList();
    }
}
