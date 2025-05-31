package com.example.inova_backend.controller;


import com.example.inova_backend.dto.MessageResponseDTO;
import com.example.inova_backend.dto.UsuarioDTO;
import com.example.inova_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UsuarioService usuarioService;

    @GetMapping("/users")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        try {
            List<UsuarioDTO> users = usuarioService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/users/{userId}/activate")
    public ResponseEntity<MessageResponseDTO> activateUser(@PathVariable Long userId) {
        try {
            usuarioService.activateUser(userId);
            return ResponseEntity.ok(new MessageResponseDTO("Usuário ativado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponseDTO("Erro ao ativar usuário: " + e.getMessage()));
        }
    }

    @PatchMapping("/users/{userId}/deactivate")
    public ResponseEntity<MessageResponseDTO> deactivateUser(@PathVariable Long userId) {
        try {
            usuarioService.deactivateUser(userId);
            return ResponseEntity.ok(new MessageResponseDTO("Usuário desativado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponseDTO("Erro ao desativar usuário: " + e.getMessage()));
        }
    }
}
