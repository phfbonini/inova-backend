package com.example.inova_backend.service;

import com.example.inova_backend.dto.PessoaDTO;
import com.example.inova_backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaDTO> getPessoas() {
        return pessoaRepository.findAll()
                .stream()
                .map(PessoaDTO::new)
                .toList();
    }

    public PessoaDTO getPessoaById(Long id) {
        return pessoaRepository.findById(id)
                .map(PessoaDTO::new)
                .orElse(null);
    }

    public PessoaDTO createPessoa(PessoaDTO pessoaDTO) {
        return new PessoaDTO(pessoaRepository.save(pessoaDTO.toEntity()));
    }

    public PessoaDTO updatePessoa(Long id, PessoaDTO pessoaDTO) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(pessoaDTO.getNome());
                    pessoa.setEmail(pessoaDTO.getEmail());
                    pessoa.setDocumentoNumero(pessoaDTO.getDocumentoNumero());
                    return new PessoaDTO(pessoaRepository.save(pessoa));
                })
                .orElse(null);
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }





}
