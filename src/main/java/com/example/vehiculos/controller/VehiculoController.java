package com.example.vehiculos.controller;

import com.example.vehiculos.dto.VehiculoDTO;
import com.example.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @GetMapping
    public List<VehiculoDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<VehiculoDTO> crear(
            @RequestPart("vehiculo") @Valid VehiculoDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        // Ya no usamos @RequestBody, sino @RequestPart para datos "multipart"
        return ResponseEntity.ok(service.guardar(dto, imagen));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> actualizar(
            @PathVariable Long id, 
            @RequestPart("vehiculo") @Valid VehiculoDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        return ResponseEntity.ok(service.actualizar(id, dto, imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
