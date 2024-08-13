package com.codecrafters.devs.controllers;

import com.codecrafters.devs.dto.CreateRecruitmentStatusDTO;
import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.dto.RecruitmentStatusDTO;
import com.codecrafters.devs.services.RecruitmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments")
public class RecruitmentStatusController {

    private final RecruitmentStatusService recruitmentStatusService;

    @Autowired
    public RecruitmentStatusController(RecruitmentStatusService recruitmentStatusService) {
        this.recruitmentStatusService = recruitmentStatusService;
    }

    @GetMapping
    public ResponseEntity<List<RecruitmentStatusDTO>> getAllRecruitmentStatuses() {
        try {
            List<RecruitmentStatusDTO> statuses = recruitmentStatusService.getAllRecruitmentStatuses();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentStatusDTO> getRecruitmentStatusById(@PathVariable Long id) {
        try {
            return recruitmentStatusService.getRecruitmentStatusById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/eligible-mutants")
    public ResponseEntity<List<MutantDTO>> getEligibleMutantsForEspada() {
        try {
            List<MutantDTO> mutants = recruitmentStatusService.getEligibleMutantsForEspada();
            return ResponseEntity.ok(mutants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{mutantId}")
    public ResponseEntity<RecruitmentStatusDTO> createRecruitmentStatus(@PathVariable Long mutantId, @RequestBody CreateRecruitmentStatusDTO dto) {
        try {
            RecruitmentStatusDTO newStatus = recruitmentStatusService.createRecruitmentStatus(mutantId, dto.enemiesDefeated());
            return ResponseEntity.status(HttpStatus.CREATED).body(newStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecruitmentStatusDTO> updateRecruitmentStatus(@PathVariable Long id, @RequestBody RecruitmentStatusDTO recruitmentStatusDTO) {
        try {
            RecruitmentStatusDTO updatedStatus = recruitmentStatusService.updateRecruitmentStatus(id, recruitmentStatusDTO);
            return ResponseEntity.ok(updatedStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruitmentStatus(@PathVariable Long id) {
        try {
            recruitmentStatusService.deleteRecruitmentStatus(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
