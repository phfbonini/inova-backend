package com.example.inova_backend.repository;

import com.example.inova_backend.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByEmail(String email);
    Pessoa findByDocumentoNumero(String documentoNumero);
}

