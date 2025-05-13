package com.example.inova_backend.dto;

import com.example.inova_backend.enums.TipoPessoa;
import com.example.inova_backend.model.Pessoa;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PessoaDTO {
    private Long id;
    private String nome;
    private String documentoNumero;
    private String tipoPessoa;
    private String email;
    private String telefone;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;

    //constructor of a partir da entidade Pessoa
    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.documentoNumero = pessoa.getDocumentoNumero();
        this.tipoPessoa = String.valueOf(pessoa.getTipoPessoa());
        this.email = pessoa.getEmail();
        this.telefone = pessoa.getTelefone();
        this.dataInclusao = pessoa.getDataInclusao();
        this.dataAlteracao = pessoa.getDataAlteracao();
    }

    public Pessoa toEntity() {
        return new Pessoa(this.nome, this.documentoNumero, TipoPessoa.valueOf(this.tipoPessoa), this.email, this.telefone);
    }
}
