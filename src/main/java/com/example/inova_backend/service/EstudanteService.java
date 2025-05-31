package com.example.inova_backend.service;

import com.example.inova_backend.model.Estudante;
import com.example.inova_backend.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;

    @Autowired
    public EstudanteService(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    public Optional<Estudante> getByPessoaId(Long pessoaId) {
        return estudanteRepository.findByPessoaId(pessoaId);
    }

    public Optional<Estudante> getById(Long id) {
        return estudanteRepository.findById(id);
    }

}
