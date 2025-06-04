package com.example.inova_backend.service;

import com.example.inova_backend.config.TokenService;
import com.example.inova_backend.dto.*;
import com.example.inova_backend.dto.auth.LoginResponseDTO;
import com.example.inova_backend.enums.Role;
import com.example.inova_backend.model.*;
import com.example.inova_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository userRepository;
    private final PessoaService pessoaService;
    private final UniversidadeService universidadeService;
    private final EmpresaService empresaService;
    private final EstudanteService estudanteService;
    private final TokenService tokenService;
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

       if (currentUserDTO.getPessoa() == null || currentUserDTO.getRole() == Role.ADMIN) {
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
                Empresa empresa = empresaService.findByPessoaId(pessoaId)
                        .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada para pessoaId: " + pessoaId));
                yield new UsuarioCompletoDTO(usuarioDTO, empresa);
            }
            case ESTUDANTE -> {
                Estudante estudante = estudanteService.findByPessoaId(pessoaId)
                        .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado para pessoaId: " + pessoaId));
                yield new UsuarioCompletoDTO(usuarioDTO, estudante);
            }
            default -> throw new IllegalArgumentException("Role não suportada: " + usuarioDTO.getRole());
        };
    }

    @Transactional
    public UsuarioCompletoDTO updateUserProfile(UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = findAndUpdateUsuario(usuarioCompletoDTO);
        Pessoa pessoa = processarPessoa(usuarioCompletoDTO);
        usuario.setPessoa(pessoa);
        usuario = userRepository.save(usuario);

        Role role = usuario.getRole();
        if (role.equals(Role.ADMIN)){
            return new UsuarioCompletoDTO(usuario);
        }

        return  switch (role) {
            case UNIVERSIDADE -> universidadeService.processarUniversidade(usuarioCompletoDTO, pessoa);
            case EMPRESA -> empresaService.processarEmpresa(usuarioCompletoDTO, pessoa);
            case ESTUDANTE -> estudanteService.processarEstudante(usuarioCompletoDTO, pessoa);
            default -> throw new IllegalArgumentException("Role não suportada: " + role);
        };

    }

    public Usuario findAndUpdateUsuario(UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = userRepository.findById(usuarioCompletoDTO.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException(USUARIO_NAO_ENCONTRADO));

        if (usuarioCompletoDTO.getUsuario().getEmail() != null) {
            usuario.setEmail(usuarioCompletoDTO.getUsuario().getEmail());
        }

        return usuario;
    }

    public Pessoa processarPessoa(UsuarioCompletoDTO usuarioCompletoDTO) {
        PessoaDTO pessoaDTOInput = usuarioCompletoDTO.getUsuario().getPessoa();
        pessoaDTOInput.setUsuarioId(usuarioCompletoDTO.getUsuario().getId());

        Long idPessoa = pessoaDTOInput.getId();
        Optional<Pessoa> pessoaOptional = Optional.empty();

        if (idPessoa != null) {
            pessoaOptional = Optional.ofNullable(pessoaService.getPessoaEntityById(idPessoa));
        }

        if (pessoaOptional.isEmpty()) {
            return pessoaService.createPessoa(pessoaDTOInput);
        } else {
            return pessoaService.updatePessoa(pessoaDTOInput);
        }
    }

    public void changeUserRole(Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));


        usuario.setRole(role);
        userRepository.save(usuario);
    }

    public LoginResponseDTO updateUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = tokenService.generateToken((Usuario) authentication.getPrincipal());
        return (new LoginResponseDTO(token));

    }
}