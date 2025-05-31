package com.example.inova_backend.repository;

import com.example.inova_backend.model.Universidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversidadeRepository extends JpaRepository<Universidade, Long> {
    Universidade findByCodigoMec(String codigoMec);

    Optional<Universidade> findByPessoaId(Long pessoaId);
}

