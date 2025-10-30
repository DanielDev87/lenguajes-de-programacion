package com.example.vehiculos.service;

import com.example.vehiculos.dto.MotoDTO;
import com.example.vehiculos.model.Moto;
import com.example.vehiculos.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoService {

    @Autowired
    private MotoRepository repository;

    public List<MotoDTO> listar() {
        return repository.findAll().stream()
                .map(m -> new MotoDTO(m.getId(), m.getMarca(), m.getModelo(), m.getAnio()))
                .collect(Collectors.toList());
    }

    public MotoDTO guardar(MotoDTO dto) {
        Moto moto = new Moto(dto.getMarca(), dto.getModelo(), dto.getAnio());
        Moto guardada = repository.save(moto);
        return new MotoDTO(guardada.getId(), guardada.getMarca(), guardada.getModelo(), guardada.getAnio());
    }

    public MotoDTO buscarPorId(Long id) {
        Moto m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));
        return new MotoDTO(m.getId(), m.getMarca(), m.getModelo(), m.getAnio());
    }

    public MotoDTO actualizar(Long id, MotoDTO dto) {
        Moto m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada"));

        m.setMarca(dto.getMarca());
        m.setModelo(dto.getModelo());
        m.setAnio(dto.getAnio());

        Moto actualizada = repository.save(m);
        return new MotoDTO(actualizada.getId(), actualizada.getMarca(), actualizada.getModelo(), actualizada.getAnio());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
