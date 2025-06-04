package com.example.inova_backend.service;

import com.example.inova_backend.dto.UsuarioCompletoDTO;
import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.model.Pessoa;
import com.example.inova_backend.model.Setor;
import com.example.inova_backend.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final SetorService setorService;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, SetorService setorService) {
        this.empresaRepository = empresaRepository;
        this.setorService = setorService;
    }

    public Optional<Empresa> findByPessoaId(Long pessoaId) {
        return empresaRepository.findByPessoaId(pessoaId);
    }

    public Optional<Empresa> getById(Long id) {
        return empresaRepository.findById(id);
    }

    public UsuarioCompletoDTO processarEmpresa(UsuarioCompletoDTO usuarioCompletoDTO, Pessoa pessoa) {
        Empresa empresa = empresaRepository
                .findByPessoaId(pessoa.getId())
                .orElseGet(Empresa::new);

        empresa.setNomeFantasia(usuarioCompletoDTO.getEmpresa().getNomeFantasia());

        Setor setor = setorService.getSetorById(usuarioCompletoDTO.getEmpresa().getSetor())
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado com ID: " + usuarioCompletoDTO.getEmpresa().getSetor()));

        empresa.setSetor(setor);
        empresa.setPessoa(pessoa);
        empresa = empresaRepository.save(empresa);
        usuarioCompletoDTO.getEmpresa().setId(empresa.getId());

        return usuarioCompletoDTO;
    }

}
