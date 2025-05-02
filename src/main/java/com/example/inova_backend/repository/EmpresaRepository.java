package com.example.inova_backend.repository;

import com.example.inova_backend.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findByNomeFantasia(String nomeFantasia);
}

