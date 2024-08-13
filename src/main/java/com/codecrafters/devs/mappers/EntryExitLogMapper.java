package com.codecrafters.devs.mappers;

import com.codecrafters.devs.dto.EntryExitLogDTO;
import com.codecrafters.devs.models.EntryExitLog;
import org.springframework.stereotype.Component;

@Component
public class EntryExitLogMapper {

    public EntryExitLogDTO toDTO(EntryExitLog entryExitLog) {
        return new EntryExitLogDTO(
                entryExitLog.getId(),
                entryExitLog.getMutant().getId(),
                entryExitLog.getEntryTime(),
                entryExitLog.getExitTime()
        );
    }

    public EntryExitLog toEntity(EntryExitLogDTO entryExitLogDTO) {
        EntryExitLog entryExitLog = new EntryExitLog();
        entryExitLog.setId(entryExitLogDTO.id());
        entryExitLog.setEntryTime(entryExitLogDTO.entryTime());
        entryExitLog.setExitTime(entryExitLogDTO.exitTime());
        return entryExitLog;
    }
}
