package com.example.vehiculos.service;

import com.example.vehiculos.dto.MotoDTO;
import com.example.vehiculos.dto.VehiculoDTO;
import com.example.vehiculos.model.Moto;
import com.example.vehiculos.model.Vehiculo;
import com.example.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository repository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<VehiculoDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VehiculoDTO guardar(VehiculoDTO dto, MultipartFile imagen) {
        Vehiculo v = new Vehiculo(dto.getMarca(), dto.getModelo(), dto.getAnio());
        if (imagen != null && !imagen.isEmpty()) {
            String nombreArchivo = fileStorageService.store(imagen);
            v.setImagenUrl(nombreArchivo); 
        }
        Vehiculo guardado = repository.save(v);
        return mapToDTO(guardado);
    }

    public VehiculoDTO buscarPorId(Long id) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return mapToDTO(v);
    }

    public VehiculoDTO actualizar(Long id, VehiculoDTO dto, MultipartFile imagen) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setAnio(dto.getAnio());
        if (imagen != null && !imagen.isEmpty()) {
            if (v.getImagenUrl() != null) {
                fileStorageService.delete(v.getImagenUrl());
            }
            String nombreArchivo = fileStorageService.store(imagen);
            v.setImagenUrl(nombreArchivo);
        }
        Vehiculo actualizado = repository.save(v);
        return mapToDTO(actualizado);
    }

    public void eliminar(Long id) {
        // (Opcional) Borrar la imagen asociada antes de eliminar la entidad
        repository.findById(id).ifPresent(v -> {
            if (v.getImagenUrl() != null) {
                fileStorageService.delete(v.getImagenUrl());
            }
            repository.delete(v);
        });
    }

    private VehiculoDTO mapToDTO(Vehiculo v) {
    String imagenUrl = v.getImagenUrl();

    if (imagenUrl != null && !imagenUrl.isEmpty() && !imagenUrl.startsWith("http")) {
        imagenUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/imagenes/")
                .path(imagenUrl)
                .toUriString();
    }

    if (v instanceof Moto) {
        return new MotoDTO(v.getId(), v.getMarca(), v.getModelo(), v.getAnio(), imagenUrl);
    }
    return new VehiculoDTO(v.getId(), v.getMarca(), v.getModelo(), v.getAnio(), imagenUrl);
}

}
