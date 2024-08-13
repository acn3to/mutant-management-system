package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRecruitmentStatusDTO(
        @NotNull Integer enemiesDefeated
) {}
