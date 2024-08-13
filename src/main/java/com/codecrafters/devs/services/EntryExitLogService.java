package com.codecrafters.devs.services;

import com.codecrafters.devs.dto.EntryExitLogDTO;
import com.codecrafters.devs.mappers.EntryExitLogMapper;
import com.codecrafters.devs.models.EntryExitLog;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.repositories.EntryExitLogRepository;
import com.codecrafters.devs.repositories.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntryExitLogService {

    private final EntryExitLogRepository entryExitLogRepository;
    private final MutantRepository mutantRepository;
    private final EntryExitLogMapper entryExitLogMapper;

    @Autowired
    public EntryExitLogService(EntryExitLogRepository entryExitLogRepository, MutantRepository mutantRepository, EntryExitLogMapper entryExitLogMapper) {
        this.entryExitLogRepository = entryExitLogRepository;
        this.mutantRepository = mutantRepository;
        this.entryExitLogMapper = entryExitLogMapper;
    }

    public List<EntryExitLogDTO> getAllLogs() {
        List<EntryExitLog> logs = entryExitLogRepository.findAll();
        return logs.stream().map(entryExitLogMapper::toDTO).toList();
    }

    public Optional<EntryExitLogDTO> getLogById(Long id) {
        return entryExitLogRepository.findById(id).map(entryExitLogMapper::toDTO);
    }

    @Transactional
    public EntryExitLogDTO createLog(Long mutantId) {
        Mutant mutant = mutantRepository.findById(mutantId)
                .orElseThrow(() -> new IllegalArgumentException("Mutant not found"));

        EntryExitLog entryExitLog = new EntryExitLog();
        entryExitLog.setMutant(mutant);
        entryExitLog.setEntryTime(LocalDateTime.now());

        mutant.setCurrentlyInSchool(true);

        EntryExitLog savedLog = entryExitLogRepository.save(entryExitLog);
        mutantRepository.save(mutant);

        return entryExitLogMapper.toDTO(savedLog);
    }

    @Transactional
    public EntryExitLogDTO updateLog(Long id, EntryExitLogDTO entryExitLogDTO) {
        return entryExitLogRepository.findById(id).map(existingLog -> {
            if (entryExitLogDTO.exitTime() != null) {
                existingLog.setExitTime(LocalDateTime.now());
                existingLog.getMutant().setCurrentlyInSchool(false);
                mutantRepository.save(existingLog.getMutant());
            } else if (existingLog.getExitTime() == null) {
                existingLog.setExitTime(LocalDateTime.now());
                existingLog.getMutant().setCurrentlyInSchool(false);
                mutantRepository.save(existingLog.getMutant());
            }

            EntryExitLog updatedLog = entryExitLogRepository.save(existingLog);
            return entryExitLogMapper.toDTO(updatedLog);
        }).orElseThrow(() -> new IllegalArgumentException("EntryExitLog not found"));
    }

    public void deleteLog(Long id) {
        if (!entryExitLogRepository.existsById(id)) {
            throw new IllegalArgumentException("EntryExitLog not found");
        }
        entryExitLogRepository.deleteById(id);
    }
}
