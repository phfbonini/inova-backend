package com.example.inova_backend.dto;

import com.example.inova_backend.model.Estudante;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudanteDTO {

    private Long id;

    private PessoaDTO pessoa;

    private Long universidade;

    private String documentoMatricula;

    private Long curso;

    private LocalDateTime dataInclusao;

    private LocalDateTime dataAlteracao;

    public EstudanteDTO(Estudante estudante) {
        this.id = estudante.getId();
        this.pessoa = new PessoaDTO(estudante.getPessoa());
        this.universidade = estudante.getUniversidade().getId();
        this.documentoMatricula = estudante.getDocumentoMatricula();
        this.curso = estudante.getCurso().getId();
    }
}
