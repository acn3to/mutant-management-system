package com.codecrafters.devs.mappers;

import com.codecrafters.devs.dto.MutantDTO;
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
                mutant.isCurrentlyInSchool()
        );
    }

    public Mutant toEntity(MutantDTO mutantDTO) {
        Mutant mutant = new Mutant();
        mutant.setName(mutantDTO.name());
        mutant.setPower(mutantDTO.power());
        mutant.setAge(mutantDTO.age());
        mutant.setCurrentlyInSchool(mutantDTO.isCurrentlyInSchool());
        return mutant;
    }
}
