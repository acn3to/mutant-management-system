package com.codecrafters.devs.mappers;

import com.codecrafters.devs.dto.CreateMutantDTO;
import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.dto.MutantPatchDTO;
import com.codecrafters.devs.enums.MutantRole;
import com.codecrafters.devs.models.Mutant;
import org.springframework.stereotype.Component;

@Component
public class MutantMapper {

    public MutantDTO toDTO(Mutant mutant) {
        return new MutantDTO(
                mutant.getId(),
                mutant.getName(),
                mutant.getPower(),
                mutant.getAge(),
                mutant.getEnemiesDefeated(),
                mutant.isCurrentlyInSchool(),
                mutant.getUsername(),
                mutant.getPassword()
        );
    }

    public Mutant toEntity(CreateMutantDTO createMutantDTO) {
        Mutant mutant = new Mutant();
        mutant.setName(createMutantDTO.name());
        mutant.setPower(createMutantDTO.power());
        mutant.setAge(createMutantDTO.age());
        mutant.setUsername(createMutantDTO.username());
        mutant.setPassword(createMutantDTO.password());

        return mutant;
    }

    public void updateEntityFromPatchDTO(Mutant mutant, MutantPatchDTO mutantPatchDTO) {
        if (mutantPatchDTO.name() != null) {
            mutant.setName(mutantPatchDTO.name());
        }
        if (mutantPatchDTO.power() != null) {
            mutant.setPower(mutantPatchDTO.power());
        }
        if (mutantPatchDTO.age() != null) {
            mutant.setAge(mutantPatchDTO.age());
        }
    }
}
