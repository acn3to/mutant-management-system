package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MutantDTO(
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String power,

        @NotNull
        Integer age,

        Integer enemiesDefeated,

        Boolean isCurrentlyInSchool
) {}