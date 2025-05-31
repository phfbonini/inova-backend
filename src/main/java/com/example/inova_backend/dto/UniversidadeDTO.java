package com.example.inova_backend.dto;

import com.example.inova_backend.model.Universidade;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversidadeDTO {

    private Long id;

    private PessoaDTO pessoa;

    private String codigoMec;

    private LocalDateTime dataInclusao;

    private LocalDateTime dataAlteracao;

    public UniversidadeDTO(Universidade universidade) {
        this.id = universidade.getId();
        this.pessoa = new PessoaDTO(universidade.getPessoa());
        this.codigoMec = universidade.getCodigoMec();
    }
}
