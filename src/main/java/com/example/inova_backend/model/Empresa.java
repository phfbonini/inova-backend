package com.example.inova_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    private String nomeFantasia;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;

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

    public Empresa(Pessoa pessoa, String nomeFantasia, Setor setor) {
        this.pessoa = pessoa;
        this.nomeFantasia = nomeFantasia;
        this.setor = setor;
    }
}

