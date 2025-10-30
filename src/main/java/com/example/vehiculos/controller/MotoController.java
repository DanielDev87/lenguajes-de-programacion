package com.example.vehiculos.controller;

import com.example.vehiculos.dto.MotoDTO;
import com.example.vehiculos.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MotoDTO> crear(@Valid @RequestBody MotoDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MotoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
