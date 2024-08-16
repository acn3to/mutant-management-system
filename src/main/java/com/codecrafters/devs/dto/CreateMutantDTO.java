package com.codecrafters.devs.dto;

import com.codecrafters.devs.enums.MutantRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMutantDTO(
        @NotBlank String name,
        @NotBlank String power,
        @NotNull Integer age,
        @NotBlank String username,
        @NotBlank String password,
        @NotNull MutantRole role
) {}
