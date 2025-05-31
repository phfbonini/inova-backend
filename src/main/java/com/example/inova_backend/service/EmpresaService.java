package com.example.inova_backend.service;

import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Optional<Empresa> getByPessoaId(Long pessoaId) {
        return empresaRepository.findByPessoaId(pessoaId);
    }

    public Optional<Empresa> getById(Long id) {
        return empresaRepository.findById(id);
    }

    // Aqui você pode adicionar métodos adicionais como save, update, delete, etc.
}
