package com.example.inova_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "universidade_id")
    private Universidade universidade;

    private String documentoMatricula;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

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

    public Estudante(Pessoa pessoa, Universidade universidade, String documentoMatricula, Curso curso) {
        this.pessoa = pessoa;
        this.universidade = universidade;
        this.documentoMatricula = documentoMatricula;
        this.curso = curso;
    }
}
