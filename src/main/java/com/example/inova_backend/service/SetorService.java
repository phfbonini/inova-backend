package com.example.inova_backend.service;

import com.example.inova_backend.dto.SetorDTO;
import com.example.inova_backend.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    public List<SetorDTO> getSetores() {
        return setorRepository.findAll()
                .stream()
                .map(SetorDTO::new)
                .toList();
    }

    public SetorDTO getSetorById(Long id) {
        return setorRepository.findById(id)
                .map(SetorDTO::new)
                .orElse(null);
    }

    public SetorDTO createSetor(SetorDTO setorDTO) {
        return new SetorDTO(setorRepository.save(setorDTO.toEntity()));
    }

    public SetorDTO updateSetor(Long id, SetorDTO setorDTO) {
        return setorRepository.findById(id)
                .map(setor -> {
                    setor.setNome(setorDTO.getNome());
                    return new SetorDTO(setorRepository.save(setor));
                })
                .orElse(null);
    }

    public void deleteSetor(Long id) {
        setorRepository.deleteById(id);
    }
}
