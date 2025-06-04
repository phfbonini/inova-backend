package com.example.inova_backend.dto;

import com.example.inova_backend.enums.TipoPessoa;
import com.example.inova_backend.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nome;
    private Long usuarioId;
    private String documentoNumero;
    private String tipoPessoa;
    private String telefone;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;

    //constructor of a partir da entidade Pessoa
    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.documentoNumero = pessoa.getDocumentoNumero();
        this.tipoPessoa = String.valueOf(pessoa.getTipoPessoa());
        this.telefone = pessoa.getTelefone();
        this.dataInclusao = pessoa.getDataInclusao();
        this.dataAlteracao = pessoa.getDataAlteracao();
    }

    public Pessoa toEntity() {
        return new Pessoa(this.nome, this.documentoNumero, TipoPessoa.valueOf(this.tipoPessoa), this.telefone);
    }
}
