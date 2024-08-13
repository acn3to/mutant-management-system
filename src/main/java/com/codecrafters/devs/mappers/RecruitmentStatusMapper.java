package com.codecrafters.devs.mappers;

import com.codecrafters.devs.dto.RecruitmentStatusDTO;
import com.codecrafters.devs.models.RecruitmentStatus;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentStatusMapper {

    public RecruitmentStatusDTO toDTO(RecruitmentStatus recruitmentStatus) {
        return new RecruitmentStatusDTO(
                recruitmentStatus.getId(),
                recruitmentStatus.getMutant().getId(),
                recruitmentStatus.isEligibleForEspada(),
                recruitmentStatus.getEnemiesDefeated(),
                recruitmentStatus.getAliensDefeated(),
                recruitmentStatus.getDemonsDefeated()
        );
    }

    public RecruitmentStatus toEntity(RecruitmentStatusDTO recruitmentStatusDTO) {
        RecruitmentStatus recruitmentStatus = new RecruitmentStatus();
        recruitmentStatus.setId(recruitmentStatusDTO.id());
        recruitmentStatus.setAliensDefeated(recruitmentStatusDTO.aliensDefeated());
        recruitmentStatus.setEnemiesDefeated(recruitmentStatusDTO.enemiesDefeated());
        recruitmentStatus.setDemonsDefeated(recruitmentStatusDTO.demonsDefeated());
        recruitmentStatus.setEligibleForEspada(recruitmentStatusDTO.isEligibleForEspada());
        return recruitmentStatus;
    }
}
