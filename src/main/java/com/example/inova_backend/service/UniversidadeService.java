package com.example.inova_backend.service;

import com.example.inova_backend.dto.UsuarioCompletoDTO;
import com.example.inova_backend.model.Pessoa;
import com.example.inova_backend.model.Universidade;
import com.example.inova_backend.repository.UniversidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversidadeService {

    private final UniversidadeRepository universidadeRepository;

    @Autowired
    public UniversidadeService(UniversidadeRepository universidadeRepository) {
        this.universidadeRepository = universidadeRepository;
    }

    public Optional<Universidade> getByPessoaId(Long pessoaId) {
        return universidadeRepository.findByPessoaId(pessoaId);
    }

    public Optional<Universidade> getById(Long id) {
        return universidadeRepository.findById(id);
    }

    public UsuarioCompletoDTO processarUniversidade(UsuarioCompletoDTO usuarioCompletoDTO, Pessoa pessoa) {
        Universidade universidade = universidadeRepository
                .findByPessoaId(pessoa.getId())
                .orElseGet(Universidade::new);

        universidade.setCodigoMec(usuarioCompletoDTO.getUniversidade().getCodigoMec());
        universidade.setPessoa(pessoa);
        universidade = universidadeRepository.save(universidade);
        usuarioCompletoDTO.getUniversidade().setId(universidade.getId());

        return usuarioCompletoDTO;
    }

}
