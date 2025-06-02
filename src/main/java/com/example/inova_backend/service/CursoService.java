package com.example.inova_backend.service;

import com.example.inova_backend.model.Curso;
import com.example.inova_backend.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }
}
