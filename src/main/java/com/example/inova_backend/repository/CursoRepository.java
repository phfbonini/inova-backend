package com.example.inova_backend.repository;

import com.example.inova_backend.model.Curso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}

