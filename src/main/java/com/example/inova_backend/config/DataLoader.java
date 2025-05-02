package com.example.inova_backend.config;

import com.example.inova_backend.enums.TipoPessoa;
import com.example.inova_backend.model.Curso;
import com.example.inova_backend.model.Empresa;
import com.example.inova_backend.model.Setor;
import com.example.inova_backend.repository.CursoRepository;
import com.example.inova_backend.repository.EmpresaRepository;
import com.example.inova_backend.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.inova_backend.model.*;
import com.example.inova_backend.repository.*;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UniversidadeRepository universidadeRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criando Setores
        Setor setor1 = new Setor("Tecnologia");
        Setor setor2 = new Setor("Marketing");
        setorRepository.save(setor1);
        setorRepository.save(setor2);

        // Criando Cursos
        Curso curso1 = new Curso("Engenharia da Computação");
        Curso curso2 = new Curso("Administração");
        cursoRepository.save(curso1);
        cursoRepository.save(curso2);

        // Criando Pessoas (para Empresas e Estudantes)
        Pessoa pessoa1 = new Pessoa("Carlos Silva", "12345678900", TipoPessoa.FISICA, "carlos@email.com", "999999999");
        Pessoa pessoa2 = new Pessoa("Tech Solutions", "12345678000195", TipoPessoa.JURIDICA, "contato@techsolutions.com", "111222333");
        Pessoa pessoa3 = new Pessoa("João Oliveira", "98765432100", TipoPessoa.FISICA, "joao@email.com", "888777666");
        Pessoa pessoa4 = new Pessoa("Marketing Agency", "98765432000182", TipoPessoa.JURIDICA, "contato@marketingagency.com", "555666777");
        pessoaRepository.save(pessoa1);
        pessoaRepository.save(pessoa2);
        pessoaRepository.save(pessoa3);
        pessoaRepository.save(pessoa4);

        // Criando Universidades
        Universidade universidade1 = new Universidade(pessoa1, "12345");
        Universidade universidade2 = new Universidade(pessoa3, "67890");
        universidadeRepository.save(universidade1);
        universidadeRepository.save(universidade2);

        // Criando Empresas com Pessoa e Setor
        Empresa empresa1 = new Empresa(pessoa2, "Tech Solutions", setor1);
        Empresa empresa2 = new Empresa( pessoa4, "Marketing Agency", setor2);
        empresaRepository.save(empresa1);
        empresaRepository.save(empresa2);

        // Criando Estudantes com Pessoa, Universidade e Curso
        Estudante estudante1 = new Estudante(pessoa1, universidade1, "123456789", curso1);
        Estudante estudante2 = new Estudante(pessoa3, universidade2, "987654321", curso2);
        estudanteRepository.save(estudante1);
        estudanteRepository.save(estudante2);

        System.out.println("Dados de exemplo inseridos!");
    }
}

