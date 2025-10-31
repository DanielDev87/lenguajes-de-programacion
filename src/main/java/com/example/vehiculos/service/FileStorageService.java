package com.example.vehiculos.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.UUID;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path rootLocation;

    // Inyectamos el valor de 'file.upload-dir' desde application.properties
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
        try {
            // Creamos el directorio si no existe
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el directorio de almacenamiento", e);
        }
    }

    /**
     * Guarda un archivo en el directorio de subida.
     * @param file El archivo MultipartFile recibido.
     * @return El nombre único generado para el archivo guardado.
     */
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("No se puede guardar un archivo vacío.");
        }

        try {
            // Generar un nombre de archivo único para evitar colisiones
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // Resolver la ruta completa del archivo
            Path destinationFile = this.rootLocation.resolve(uniqueFilename);

            // Copiar el stream del archivo a su destino
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Devolvemos solo el nombre único del archivo
            return uniqueFilename;

        } catch (IOException e) {
            throw new RuntimeException("Falló al guardar el archivo.", e);
        }
    }

    // (Opcional) Añadir un método para borrar archivos cuando se elimine un vehículo
    public void delete(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            // Manejar el error, quizás solo loggearlo
            System.err.println("No se pudo borrar el archivo: " + filename);
        }
    }
}
