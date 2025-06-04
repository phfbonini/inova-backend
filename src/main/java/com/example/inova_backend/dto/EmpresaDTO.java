package com.example.inova_backend.dto;

import com.example.inova_backend.model.Empresa;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDTO {

    private Long id;

    private PessoaDTO pessoa;

    private String nomeFantasia;

    private Long setor;

    private LocalDateTime dataInclusao;

    private LocalDateTime dataAlteracao;

    public EmpresaDTO (Empresa empresa) {
        this.id = empresa.getId();
        this.pessoa = new PessoaDTO(empresa.getPessoa());
        this.nomeFantasia = empresa.getNomeFantasia();
        this.setor = empresa.getSetor().getId();
    }
}
