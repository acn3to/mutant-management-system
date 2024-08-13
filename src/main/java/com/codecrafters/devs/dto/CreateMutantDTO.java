package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMutantDTO(
        @NotBlank
        String name,

        @NotBlank
        String power,

        @NotNull
        Integer age
) {}
