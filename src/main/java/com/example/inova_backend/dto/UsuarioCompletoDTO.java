package com.example.inova_backend.dto;

import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.model.Estudante;
import com.example.inova_backend.model.Universidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCompletoDTO {
    private UsuarioDTO usuario;
    private EstudanteDTO estudante;
    private EmpresaDTO empresa;
    private UniversidadeDTO universidade;

    //usuario completo a partir de usuario e estudante
    public UsuarioCompletoDTO(UsuarioDTO usuario, Universidade universidade) {
        this.usuario = usuario;
        this.universidade = new UniversidadeDTO(universidade);
    }

    //usuario completo a partir de usuario e empresa
    public UsuarioCompletoDTO(UsuarioDTO usuario, Empresa empresa) {
        this.usuario = usuario;
        this.empresa = new EmpresaDTO(empresa);
    }

    //usuario completo a partir de usuario e estudante
    public UsuarioCompletoDTO(UsuarioDTO usuario, Estudante estudante) {
        this.usuario = usuario;
        this.estudante = new EstudanteDTO(estudante);
    }
}

