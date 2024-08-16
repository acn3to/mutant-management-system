package com.codecrafters.devs.controllers;

import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.dto.MutantPatchDTO;
import com.codecrafters.devs.services.MutantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mutants")
public class MutantController {

    @Autowired
    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @GetMapping
    public ResponseEntity<List<MutantDTO>> getAllMutants() {
        try {
            List<MutantDTO> mutants = mutantService.getAllMutants();
            return ResponseEntity.ok(mutants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MutantDTO> getMutantById(@PathVariable Long id) {
        try {
            Optional<MutantDTO> mutant = mutantService.getMutantById(id);
            return mutant.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/in-school")
    public ResponseEntity<List<MutantDTO>> getAllMutantsInSchool() {
        try {
            List<MutantDTO> mutantDTOs = mutantService.getAllMutantsInSchool().stream()
                    .map(mutant -> new MutantDTO(
                            mutant.getId(),
                            mutant.getName(),
                            mutant.getPower(),
                            mutant.getAge(),
                            mutant.getEnemiesDefeated(),
                            mutant.isCurrentlyInSchool(),
                            mutant.getUsername(),
                            mutant.getPassword()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(mutantDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count-in-school")
    public ResponseEntity<Map<String, Integer>> getCountOfMutantsInSchool() {
        try {
            int count = mutantService.getCountOfMutantsInSchool();
            Map<String, Integer> response = new HashMap<>();
            response.put("mutants_in_school", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MutantDTO> updateMutant(@PathVariable Long id, @RequestBody @Valid MutantPatchDTO mutantPatchDTO) {
        try {
            Optional<MutantDTO> updatedMutant = mutantService.updateMutant(id, mutantPatchDTO);
            return updatedMutant.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMutant(@PathVariable Long id) {
        try {
            mutantService.deleteMutant(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
