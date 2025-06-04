package com.example.inova_backend.controller;

import com.example.inova_backend.dto.MessageResponseDTO;
import com.example.inova_backend.dto.UsuarioCompletoDTO;
import com.example.inova_backend.dto.UsuarioStatusDTO;
import com.example.inova_backend.enums.Role;
import com.example.inova_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ESTUDANTE') or hasRole('EMPRESA') or hasRole('UNIVERSIDADE')")
    public ResponseEntity<UsuarioStatusDTO> getCurrentUserStatus() {
        try {
            UsuarioStatusDTO status = usuarioService.getCurrentUserStatus();
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //get que recebe a role no parametro

    @GetMapping("/profile")
    //role de estar autenticado apenas
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioCompletoDTO> getUserProfileByRole() {
        UsuarioCompletoDTO userProfile = usuarioService.getUserProfileByRole();
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioCompletoDTO> updateUserProfile(@RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        UsuarioCompletoDTO updatedProfile = usuarioService.updateUserProfile(usuarioCompletoDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    //metodo que altera a role do usuario
    @PatchMapping("/role")
    public ResponseEntity<MessageResponseDTO> changeUserRole(@RequestParam Role role){
        usuarioService.changeUserRole(role);
        return ResponseEntity.ok(new MessageResponseDTO("Role do usuário alterada com sucesso"));
    }

}
