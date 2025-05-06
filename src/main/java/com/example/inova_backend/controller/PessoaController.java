package com.example.inova_backend.controller;

import com.example.inova_backend.dto.PessoaDTO;
import com.example.inova_backend.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<PessoaDTO> getPessoas() {
        return pessoaService.getPessoas();
    }
}
