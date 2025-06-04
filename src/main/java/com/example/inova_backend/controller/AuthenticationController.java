package com.example.inova_backend.controller;

import com.example.inova_backend.config.TokenService;
import com.example.inova_backend.dto.auth.AuthenticationDTO;
import com.example.inova_backend.dto.auth.LoginResponseDTO;
import com.example.inova_backend.dto.auth.RegisterDTO;
import com.example.inova_backend.model.Usuario;
import com.example.inova_backend.repository.UsuarioRepository;
import com.example.inova_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.email(), encryptedPassword, data.role());
        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/context")
    public ResponseEntity<LoginResponseDTO> getContext() {
        return  ResponseEntity.ok(usuarioService.updateUserContext());
    }
}
