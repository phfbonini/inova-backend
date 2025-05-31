package com.example.inova_backend.service;

import com.example.inova_backend.dto.*;
import com.example.inova_backend.enums.Role;
import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.model.Estudante;
import com.example.inova_backend.model.Universidade;
import com.example.inova_backend.model.Usuario;
import com.example.inova_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository userRepository;
    private final PessoaService pessoaService;
    private final UniversidadeService universidadeService;
    private final EmpresaService empresaService;
    private final EstudanteService estudanteService;
    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

    public UsuarioStatusDTO getCurrentUserStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

        return new UsuarioStatusDTO(user.getAtivo());
    }

    public List<UsuarioDTO> getAllUsers() {
        List<Usuario> users = userRepository.findAll();
        return users.stream()
                .map(Usuario::toDTO)
                .toList();
    }

    @Transactional
    public void activateUser(Long userId) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

        user.setAtivo(true);
        userRepository.save(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

        if (user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Não é possível desativar usuário administrador");
        }

        user.setAtivo(false);
        userRepository.save(user);
    }

    private UsuarioDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));
        return user.toDTO();
    }

    public UsuarioCompletoDTO getUserProfileByRole() {
       UsuarioDTO currentUserDTO = this.getCurrentUser();

       if (currentUserDTO.getPessoa() == null) {
           return new UsuarioCompletoDTO(currentUserDTO, null, null, null);
       }

       return this.populateUsuarioCompletoDTO(currentUserDTO);
    }

    private UsuarioCompletoDTO populateUsuarioCompletoDTO(UsuarioDTO usuarioDTO) {
        Long pessoaId = usuarioDTO.getId();

        return switch (usuarioDTO.getRole()) {
            case UNIVERSIDADE -> {
                Universidade universidade = universidadeService.getByPessoaId(pessoaId)
                        .orElseThrow(() -> new EntityNotFoundException("Universidade não encontrada para pessoaId: " + pessoaId));
                yield new UsuarioCompletoDTO(usuarioDTO, universidade);
            }
            case EMPRESA -> {
                Empresa empresa = empresaService.getByPessoaId(pessoaId)
                        .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada para pessoaId: " + pessoaId));
                yield new UsuarioCompletoDTO(usuarioDTO, empresa);
            }
            case ESTUDANTE -> {
                Estudante estudante = estudanteService.getByPessoaId(pessoaId)
                        .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado para pessoaId: " + pessoaId));
                yield new UsuarioCompletoDTO(usuarioDTO, estudante);
            }
            default -> throw new IllegalArgumentException("Role não suportada: " + usuarioDTO.getRole());
        };
    }



}