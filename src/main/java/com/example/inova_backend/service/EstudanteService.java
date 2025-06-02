package com.example.inova_backend.service;

import com.example.inova_backend.dto.UsuarioCompletoDTO;
import com.example.inova_backend.model.Curso;
import com.example.inova_backend.model.Estudante;
import com.example.inova_backend.model.Pessoa;
import com.example.inova_backend.model.Universidade;
import com.example.inova_backend.repository.EstudanteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;
    private final UniversidadeService universidadeService;
    private final CursoService cursoService;

    @Autowired
    public EstudanteService(EstudanteRepository estudanteRepository, UniversidadeService universidadeService, CursoService cursoService) {
        this.estudanteRepository = estudanteRepository;
        this.universidadeService = universidadeService;
        this.cursoService = cursoService;
    }

    public Optional<Estudante> findByPessoaId(Long pessoaId) {
        return estudanteRepository.findByPessoaId(pessoaId);
    }

    public UsuarioCompletoDTO processarEstudante(UsuarioCompletoDTO usuarioCompletoDTO, Pessoa pessoa) {
        Long universidadeId = usuarioCompletoDTO.getEstudante().getUniversidade();
        Long cursoId = usuarioCompletoDTO.getEstudante().getCurso();

        Universidade universidade = universidadeService.getById(universidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Universidade não encontrada com ID: " + universidadeId));

        Curso curso = cursoService.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + cursoId));

        Estudante estudante = estudanteRepository
                .findByPessoaId(pessoa.getId())
                .orElseGet(Estudante::new);

        estudante.setPessoa(pessoa);
        estudante.setUniversidade(universidade);
        estudante.setDocumentoMatricula(usuarioCompletoDTO.getEstudante().getDocumentoMatricula());
        estudante.setCurso(curso);
        estudante = estudanteRepository.save(estudante);
        usuarioCompletoDTO.getEstudante().setId(estudante.getId());

        return usuarioCompletoDTO;
    }

}
