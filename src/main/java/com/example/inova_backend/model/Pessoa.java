package com.example.inova_backend.model;

import com.example.inova_backend.enums.TipoPessoa;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nome;
    private String documentoNumero;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
    private String telefone;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;

    @PrePersist
    public void prePersist() {
        this.dataInclusao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAlteracao = LocalDateTime.now();
    }

    public Pessoa(String nome, String documentoNumero, TipoPessoa tipoPessoa, String telefone) {
        this.nome = nome;
        this.documentoNumero = documentoNumero;
        this.tipoPessoa = tipoPessoa;
        this.telefone = telefone;
    }

}


