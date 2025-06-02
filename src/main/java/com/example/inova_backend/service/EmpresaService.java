package com.example.inova_backend.service;

import com.example.inova_backend.dto.UsuarioCompletoDTO;
import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.model.Pessoa;
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
        empresa.setSetor(usuarioCompletoDTO.getEmpresa().getSetor());
        empresa.setPessoa(pessoa);
        empresa = empresaRepository.save(empresa);
        usuarioCompletoDTO.getEmpresa().setId(empresa.getId());

        return usuarioCompletoDTO;
    }

}
