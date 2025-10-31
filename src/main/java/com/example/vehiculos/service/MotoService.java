package com.example.vehiculos.service;

import com.example.vehiculos.dto.MotoDTO;
import com.example.vehiculos.model.Moto;
import com.example.vehiculos.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoService {

    @Autowired
    private MotoRepository repository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<MotoDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO) 
                .collect(Collectors.toList());
    }

    public MotoDTO guardar(MotoDTO dto, MultipartFile imagen) {
        Moto moto = new Moto(dto.getMarca(), dto.getModelo(), dto.getAnio());
        
        if (imagen != null && !imagen.isEmpty()) {
            String nombreArchivo = fileStorageService.store(imagen);
            moto.setImagenUrl(nombreArchivo); 
        }

        Moto guardada = repository.save(moto);
        return mapToDTO(guardada);
    }

    public MotoDTO buscarPorId(Long id) {
        Moto m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));
        return mapToDTO(m);
    }

    public MotoDTO actualizar(Long id, MotoDTO dto, MultipartFile imagen) {
        Moto m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));

        m.setMarca(dto.getMarca());
        m.setModelo(dto.getModelo());
        m.setAnio(dto.getAnio());
        
        if (imagen != null && !imagen.isEmpty()) {
            if (m.getImagenUrl() != null) {
                fileStorageService.delete(m.getImagenUrl());
            }
            String nombreArchivo = fileStorageService.store(imagen);
            m.setImagenUrl(nombreArchivo);
        }

        Moto actualizada = repository.save(m);
        return mapToDTO(actualizada);
    }

    public void eliminar(Long id) {
         repository.findById(id).ifPresent(m -> {
            if (m.getImagenUrl() != null) {
                fileStorageService.delete(m.getImagenUrl());
            }
            repository.delete(m);
        });
    }

    private MotoDTO mapToDTO(Moto m) {
    String imagenUrl = m.getImagenUrl();

    // Evita duplicar la URL si ya es absoluta
    if (imagenUrl != null && !imagenUrl.isEmpty() && !imagenUrl.startsWith("http")) {
        imagenUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/imagenes/")
                .path(imagenUrl)
                .toUriString();
    }

    return new MotoDTO(m.getId(), m.getMarca(), m.getModelo(), m.getAnio(), imagenUrl);
}

}
