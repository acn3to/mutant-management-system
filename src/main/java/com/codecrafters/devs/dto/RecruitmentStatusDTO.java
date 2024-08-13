package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotNull;

public record RecruitmentStatusDTO(
        Long id,

        @NotNull
        Long mutantId,

        @NotNull
        Boolean isEligibleForEspada,

        @NotNull
        Integer enemiesDefeated,

        @NotNull
        Integer aliensDefeated,

        @NotNull
        Integer demonsDefeated
) {}
