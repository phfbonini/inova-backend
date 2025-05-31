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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository userRepository;

    public UsuarioStatusDTO getCurrentUserStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setAtivo(true);
        userRepository.save(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Não é possível desativar usuário administrador");
        }

        user.setAtivo(false);
        userRepository.save(user);
    }

}