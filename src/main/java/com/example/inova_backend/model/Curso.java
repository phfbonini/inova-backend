package com.example.inova_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

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

    public Curso(String nome) {
        this.nome = nome;
    }

}

