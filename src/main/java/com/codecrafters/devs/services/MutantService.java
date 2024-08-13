package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.mappers.MutantMapper;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.repositories.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MutantService {

    //TODO: Remove this mapper injection
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

    public MutantDTO createMutant(MutantDTO mutantDTO) {
        Mutant mutant = mutantMapper.toEntity(mutantDTO);
        Mutant savedMutant = mutantRepository.save(mutant);
        return mutantMapper.toDTO(savedMutant);
    }

    public Optional<MutantDTO> updateMutant(Long id, MutantDTO mutantDTO) {
        return mutantRepository.findById(id).map(existingMutant -> {
            if (mutantDTO.name() != null) {
                existingMutant.setName(mutantDTO.name());
            }
            if (mutantDTO.power() != null) {
                existingMutant.setPower(mutantDTO.power());
            }
            if (mutantDTO.age() != null) {
                existingMutant.setAge(mutantDTO.age());
            }

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
