package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotNull;

public record RecruitmentStatusDTO(

        Long id,

        @NotNull
        Long mutantId,

        @NotNull
        Boolean isEligibleForRecruitment,

        @NotNull
        Integer aliensDefeated,

        @NotNull
        Integer demonsDefeated
) {}
