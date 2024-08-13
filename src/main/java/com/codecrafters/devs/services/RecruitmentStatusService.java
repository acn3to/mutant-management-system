package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.RecruitmentStatusDTO;
import com.codecrafters.devs.mappers.RecruitmentStatusMapper;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.models.RecruitmentStatus;
import com.codecrafters.devs.repositories.MutantRepository;
import com.codecrafters.devs.repositories.RecruitmentStatusRepository;
import org.springframework.beans.BeanUtils;
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

    public RecruitmentStatusDTO createRecruitmentStatus(Long mutantId) {
        Mutant mutant = mutantRepository.findById(mutantId).orElseThrow(() -> new IllegalArgumentException("Mutant not found"));

        RecruitmentStatus recruitmentStatus = new RecruitmentStatus();
        recruitmentStatus.setMutant(mutant);
        recruitmentStatus.setEnemiesDefeated(mutant.getEnemiesDefeated());

        int aliensDefeated = (int) Math.round(mutant.getEnemiesDefeated() * (26.8 / 100));
        int demonsDefeated = (int) Math.round(mutant.getEnemiesDefeated() * (43.2 / 100));

        recruitmentStatus.setAliensDefeated(aliensDefeated);
        recruitmentStatus.setDemonsDefeated(demonsDefeated);
        recruitmentStatus.setEligibleForEspada(aliensDefeated > 20);

        RecruitmentStatus savedStatus = recruitmentStatusRepository.save(recruitmentStatus);
        return recruitmentStatusMapper.toDTO(savedStatus);
    }

    public RecruitmentStatusDTO updateRecruitmentStatus(Long id, RecruitmentStatusDTO recruitmentStatusDTO) {
        return recruitmentStatusRepository.findById(id).map(existingStatus -> {
            BeanUtils.copyProperties(recruitmentStatusDTO, existingStatus, "id", "mutant");

            int enemiesDefeated = existingStatus.getAliensDefeated() + existingStatus.getDemonsDefeated();
            existingStatus.setEnemiesDefeated(enemiesDefeated);

            RecruitmentStatus updatedStatus = recruitmentStatusRepository.save(existingStatus);
            return recruitmentStatusMapper.toDTO(updatedStatus);
        }).orElseThrow(() -> new IllegalArgumentException("RecruitmentStatus not found"));
    }

    public void deleteRecruitmentStatus(Long id) {
        if (!recruitmentStatusRepository.existsById(id)) {
            throw new IllegalArgumentException("RecruitmentStatus not found");
        }
        recruitmentStatusRepository.deleteById(id);
    }
}
