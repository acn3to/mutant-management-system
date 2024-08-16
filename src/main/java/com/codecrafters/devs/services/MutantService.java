package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.CreateMutantDTO;
import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.dto.MutantPatchDTO;
import com.codecrafters.devs.mappers.MutantMapper;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.repositories.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MutantService {

    private final MutantRepository mutantRepository;
    private final MutantMapper mutantMapper;

    @Autowired
    public MutantService(MutantRepository mutantRepository, MutantMapper mutantMapper) {
        this.mutantRepository = mutantRepository;
        this.mutantMapper = mutantMapper;
    }

    public List<MutantDTO> getAllMutants() {
        List<Mutant> mutants = mutantRepository.findAll();
        return mutants.stream().map(mutantMapper::toDTO).toList();
    }

    public Optional<MutantDTO> getMutantById(Long id) {
        return mutantRepository.findById(id).map(mutantMapper::toDTO);
    }

    public List<Mutant> getAllMutantsInSchool() {
        return mutantRepository.findByIsCurrentlyInSchoolTrue();
    }

    public int getCountOfMutantsInSchool() {
        return mutantRepository.countByIsCurrentlyInSchoolTrue();
    }

    public MutantDTO createMutant(CreateMutantDTO createMutantDTO) {
        Mutant mutant = mutantMapper.toEntity(createMutantDTO);

        Mutant savedMutant = mutantRepository.save(mutant);
        return mutantMapper.toDTO(savedMutant);
    }

    public Optional<MutantDTO> updateMutant(Long id, MutantPatchDTO mutantPatchDTO) {
        return mutantRepository.findById(id).map(existingMutant -> {
            mutantMapper.updateEntityFromPatchDTO(existingMutant, mutantPatchDTO);
            Mutant updatedMutant = mutantRepository.save(existingMutant);
            return mutantMapper.toDTO(updatedMutant);
        });
    }

    public void deleteMutant(Long id) {
        if (!mutantRepository.existsById(id)) {
            throw new IllegalArgumentException("Mutant not found");
        }
        mutantRepository.deleteById(id);
    }
}
