package com.example.inova_backend.enums;

public enum TipoPessoa {
    FISICA("FISICA"),
    JURIDICA("JURIDICA");

    private final String descricao;

    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

