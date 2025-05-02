package com.example.inova_backend.repository;

import com.example.inova_backend.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    Estudante findByDocumentoMatricula(String documentoMatricula);
}
