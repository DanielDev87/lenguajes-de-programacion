package com.example.vehiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "motos")
public class Moto extends Vehiculo {


    public Moto() {}

    public Moto(String marca, String modelo, Integer anio) {
        super(marca, modelo, anio);

    }

   
}
