package com.codecrafters.devs.controllers;

import com.codecrafters.devs.dto.EntryExitLogDTO;
import com.codecrafters.devs.services.EntryExitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entry-exit-logs")
public class EntryExitLogController {

    private final EntryExitLogService entryExitLogService;

    @Autowired
    public EntryExitLogController(EntryExitLogService entryExitLogService) {
        this.entryExitLogService = entryExitLogService;
    }

    @GetMapping
    public ResponseEntity<List<EntryExitLogDTO>> getAllLogs() {
        try {
            List<EntryExitLogDTO> logs = entryExitLogService.getAllLogs();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryExitLogDTO> getLogById(@PathVariable Long id) {
        try {
            return entryExitLogService.getLogById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create/{mutantId}")
    public ResponseEntity<EntryExitLogDTO> createLog(@PathVariable Long mutantId) {
        try {
            EntryExitLogDTO createdLog = entryExitLogService.createLog(mutantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLog);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntryExitLogDTO> updateLog(@PathVariable Long id, @RequestBody EntryExitLogDTO entryExitLogDTO) {
        try {
            EntryExitLogDTO updatedLog = entryExitLogService.updateLog(id, entryExitLogDTO);
            return ResponseEntity.ok(updatedLog);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        try {
            entryExitLogService.deleteLog(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
