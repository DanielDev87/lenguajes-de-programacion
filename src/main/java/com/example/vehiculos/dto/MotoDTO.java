package com.example.vehiculos.dto;

public class MotoDTO extends VehiculoDTO {
    public MotoDTO() {}
    public MotoDTO(Long id, String marca, String modelo, Integer anio, String imagenUrl) {
        super(id, marca, modelo, anio, imagenUrl);
    }
}

