package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.RecruitmentStatusDTO;
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

@Service
public class RecruitmentStatusService {

    private final RecruitmentStatusRepository recruitmentStatusRepository;
    private final MutantRepository mutantRepository;
    private final RecruitmentStatusMapper recruitmentStatusMapper;

    @Autowired
    public RecruitmentStatusService(RecruitmentStatusRepository recruitmentStatusRepository, MutantRepository mutantRepository, RecruitmentStatusMapper recruitmentStatusMapper) {
        this.recruitmentStatusRepository = recruitmentStatusRepository;
        this.mutantRepository = mutantRepository;
        this.recruitmentStatusMapper = recruitmentStatusMapper;
    }

    public List<RecruitmentStatusDTO> getAllRecruitmentStatuses() {
        List<RecruitmentStatus> statuses = recruitmentStatusRepository.findAll();
        return statuses.stream().map(recruitmentStatusMapper::toDTO).toList();
    }

    public Optional<RecruitmentStatusDTO> getRecruitmentStatusById(Long id) {
        return recruitmentStatusRepository.findById(id).map(recruitmentStatusMapper::toDTO);
    }

    public RecruitmentStatusDTO createRecruitmentStatus(Long mutantId, int enemiesDefeated) {
        Mutant mutant = mutantRepository.findById(mutantId).orElseThrow(() -> new IllegalArgumentException("Mutant not found"));

        RecruitmentStatus recruitmentStatus = RecruitmentStatusHelper.calculateRecruitmentStatus(mutant, enemiesDefeated);

        mutantRepository.save(mutant);
        RecruitmentStatus savedStatus = recruitmentStatusRepository.save(recruitmentStatus);

        return recruitmentStatusMapper.toDTO(savedStatus);
    }

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
