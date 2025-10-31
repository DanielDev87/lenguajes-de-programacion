package com.example.vehiculos.controller;

import com.example.vehiculos.dto.MotoDTO;
import com.example.vehiculos.dto.VehiculoDTO;
import com.example.vehiculos.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService service;

    @GetMapping
    public List<MotoDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<MotoDTO> crear(
            @RequestPart("moto") @Valid MotoDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        // Ya no usamos @RequestBody, sino @RequestPart para datos "multipart"
        return ResponseEntity.ok(service.guardar(dto, imagen));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> actualizar(
            @PathVariable Long id, 
            @RequestPart("moto") @Valid MotoDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        return ResponseEntity.ok(service.actualizar(id, dto, imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
