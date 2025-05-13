package com.example.inova_backend.dto;

import com.example.inova_backend.model.Setor;
import lombok.Data;

@Data
public class SetorDTO {

    private Long id;
    private String nome;

    public Setor toEntity() {
        return new Setor(this.nome);
    }

    public SetorDTO(Setor setor) {
        this.id = setor.getId();
        this.nome = setor.getNome();
    }
}
