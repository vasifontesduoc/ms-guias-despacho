package com.duoc.ms_guias_despacho.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Service
public class S3Service {

    private final String bucketName = "ms-guias-despacho";

    private final S3Client s3Client = S3Client.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    public String subirArchivo(MultipartFile archivo) {

        try {

            String nombreArchivo = archivo.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nombreArchivo)
                    .contentType(archivo.getContentType())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(archivo.getBytes()));

            return "Archivo subido correctamente: " + nombreArchivo;

        } catch (Exception e) {

            throw new RuntimeException("Error al subir archivo a S3");
        }
    }

    public byte[] descargarArchivo(String nombreArchivo) {

        try {

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nombreArchivo)
                    .build();

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);

            return response.readAllBytes();

        } catch (Exception e) {

            throw new RuntimeException("Error al descargar archivo desde S3");
        }
    }

    public List<String> listarArchivos() {

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        return s3Client.listObjectsV2(request)
                .contents()
                .stream()
                .map(S3Object::key)
                .toList();
    }
}