package com.example.vehiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "motos")
public class Moto extends Vehiculo {

    @NotNull(message = "El tipo de moto es obligatorio")
    @Size(min = 2, max = 50, message = "El tipo de moto debe tener entre 2 y 50 caracteres")
    private String tipo;

    public Moto() {}

    public Moto(String marca, String modelo, Integer anio, String tipo) {
        super(marca, modelo, anio);
        this.tipo = tipo;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
