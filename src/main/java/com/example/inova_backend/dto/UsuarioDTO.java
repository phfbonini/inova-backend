package com.example.inova_backend.dto;

import com.example.inova_backend.enums.Role;
import com.example.inova_backend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private Role role;
    private PessoaDTO pessoa;
    private Boolean isActive;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;

    // Constructor from entity Usuario
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.pessoa = new PessoaDTO(usuario.getPessoa());
    }
}


