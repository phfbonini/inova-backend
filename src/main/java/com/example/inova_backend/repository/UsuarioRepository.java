package com.example.inova_backend.repository;

import com.example.inova_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
