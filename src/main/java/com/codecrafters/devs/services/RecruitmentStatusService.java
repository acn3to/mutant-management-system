package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.MutantDTO;
import com.codecrafters.devs.dto.RecruitmentStatusDTO;
import com.codecrafters.devs.mappers.MutantMapper;
import com.codecrafters.devs.mappers.RecruitmentStatusMapper;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.models.RecruitmentStatus;
import com.codecrafters.devs.repositories.MutantRepository;
import com.codecrafters.devs.repositories.RecruitmentStatusRepository;
import com.codecrafters.devs.helpers.RecruitmentStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecruitmentStatusService {

    private final RecruitmentStatusRepository recruitmentStatusRepository;
    private final MutantRepository mutantRepository;
    private final RecruitmentStatusMapper recruitmentStatusMapper;
    private final MutantMapper mutantMapper;

    @Autowired
    public RecruitmentStatusService(RecruitmentStatusRepository recruitmentStatusRepository, MutantRepository mutantRepository, RecruitmentStatusMapper recruitmentStatusMapper, MutantMapper mutantMapper) {
        this.recruitmentStatusRepository = recruitmentStatusRepository;
        this.mutantRepository = mutantRepository;
        this.recruitmentStatusMapper = recruitmentStatusMapper;
        this.mutantMapper = mutantMapper;
    }

    public List<RecruitmentStatusDTO> getAllRecruitmentStatuses() {
        List<RecruitmentStatus> statuses = recruitmentStatusRepository.findAll();
        return statuses.stream().map(recruitmentStatusMapper::toDTO).toList();
    }

    public Optional<RecruitmentStatusDTO> getRecruitmentStatusById(Long id) {
        return recruitmentStatusRepository.findById(id).map(recruitmentStatusMapper::toDTO);
    }

    public List<MutantDTO> getEligibleMutantsForEspada() {
        List<RecruitmentStatus> eligibleStatuses = recruitmentStatusRepository.findAllByIsEligibleForEspadaTrue();

        List<Long> eligibleMutantIds = eligibleStatuses.stream().map(status -> status.getMutant().getId()).collect(Collectors.toList());

        return mutantRepository.findAllById(eligibleMutantIds).stream().map(mutantMapper::toDTO).collect(Collectors.toList());
    }

    public RecruitmentStatusDTO createRecruitmentStatus(Long mutantId, int enemiesDefeated) {
        Mutant mutant = mutantRepository.findById(mutantId).orElseThrow(() -> new IllegalArgumentException("Mutant not found"));

        RecruitmentStatus recruitmentStatus = RecruitmentStatusHelper.calculateRecruitmentStatus(mutant, enemiesDefeated);

        mutantRepository.save(mutant);
        RecruitmentStatus savedStatus = recruitmentStatusRepository.save(recruitmentStatus);

        return recruitmentStatusMapper.toDTO(savedStatus);
    }

    //TODO: Refactor this
    public RecruitmentStatusDTO updateRecruitmentStatus(Long id, RecruitmentStatusDTO recruitmentStatusDTO) {
        return recruitmentStatusRepository.findById(id).map(existingStatus -> {
            if (recruitmentStatusDTO.enemiesDefeated() != null) {
                int newEnemiesDefeated = recruitmentStatusDTO.enemiesDefeated();

                Mutant mutant = existingStatus.getMutant();

                mutant.setEnemiesDefeated(newEnemiesDefeated);

                RecruitmentStatus updatedStatus = RecruitmentStatusHelper.calculateRecruitmentStatus(mutant, newEnemiesDefeated);

                existingStatus.setEnemiesDefeated(updatedStatus.getEnemiesDefeated());
                existingStatus.setAliensDefeated(updatedStatus.getAliensDefeated());
                existingStatus.setDemonsDefeated(updatedStatus.getDemonsDefeated());
                existingStatus.setEligibleForEspada(updatedStatus.isEligibleForEspada());

                mutantRepository.save(mutant);
                RecruitmentStatus savedStatus = recruitmentStatusRepository.save(existingStatus);

                return recruitmentStatusMapper.toDTO(savedStatus);
            } else {
                throw new IllegalArgumentException("Only 'enemiesDefeated' can be updated.");
            }
        }).orElseThrow(() -> new IllegalArgumentException("RecruitmentStatus not found"));
    }

    public void deleteRecruitmentStatus(Long id) {
        RecruitmentStatus status = recruitmentStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RecruitmentStatus not found"));

        Mutant mutant = status.getMutant();

        mutant.setEnemiesDefeated(0);
        mutant.setRecruitmentStatus(null);

        mutantRepository.save(mutant);

        recruitmentStatusRepository.deleteById(id);
    }
}
