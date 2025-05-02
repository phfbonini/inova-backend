package com.example.inova_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Universidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    private String codigoMec;

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

    public Universidade(Pessoa pessoa, String codigoMec) {
        this.pessoa = pessoa;
        this.codigoMec = codigoMec;
    }
}

