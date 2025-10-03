package com.example.vehiculos.service;

import com.example.vehiculos.dto.VehiculoDTO;
import com.example.vehiculos.model.Vehiculo;
import com.example.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository repository;

    public List<VehiculoDTO> listar() {
        return repository.findAll().stream()
                .map(v -> new VehiculoDTO(v.getId(), v.getMarca(), v.getModelo(), v.getAnio()))
                .collect(Collectors.toList());
    }

    public VehiculoDTO guardar(VehiculoDTO dto) {
        Vehiculo v = new Vehiculo(dto.getMarca(), dto.getModelo(), dto.getAnio());
        Vehiculo guardado = repository.save(v);
        return new VehiculoDTO(guardado.getId(), guardado.getMarca(), guardado.getModelo(), guardado.getAnio());
    }

    public VehiculoDTO buscarPorId(Long id) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return new VehiculoDTO(v.getId(), v.getMarca(), v.getModelo(), v.getAnio());
    }

    public VehiculoDTO actualizar(Long id, VehiculoDTO dto) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setAnio(dto.getAnio());
        Vehiculo actualizado = repository.save(v);
        return new VehiculoDTO(actualizado.getId(), actualizado.getMarca(), actualizado.getModelo(), actualizado.getAnio());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
