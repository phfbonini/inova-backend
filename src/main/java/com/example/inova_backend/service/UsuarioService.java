package com.example.inova_backend.service;

import com.example.inova_backend.dto.UsuarioDTO;
import com.example.inova_backend.dto.UsuarioStatusDTO;
import com.example.inova_backend.enums.Role;
import com.example.inova_backend.model.Usuario;
import com.example.inova_backend.repository.UsuarioRepository;
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

}