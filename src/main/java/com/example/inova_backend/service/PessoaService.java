package com.example.inova_backend.service;

import com.example.inova_backend.dto.PessoaDTO;
import com.example.inova_backend.model.Pessoa;
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

    public Pessoa getPessoaEntityById(Long id) {
        return pessoaRepository.findById(id)
                .orElse(null);
    }


    public Pessoa createPessoa(PessoaDTO pessoaDTO) {
        return pessoaRepository.save(pessoaDTO.toEntity());
    }

    public Pessoa updatePessoa(PessoaDTO pessoaDTO) {
        return pessoaRepository.save(pessoaDTO.toEntity());
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

}
