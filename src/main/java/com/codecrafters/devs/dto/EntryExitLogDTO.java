package com.codecrafters.devs.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EntryExitLogDTO(
        Long id,

        @NotNull
        Long mutantId,

        @NotNull
        LocalDateTime entryTime,

        LocalDateTime exitTime
) {}
