package com.codecrafters.devs.repositories;

import com.codecrafters.devs.models.EntryExitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntryExitLogRepository extends JpaRepository<EntryExitLog, Long> {
    // TODO: Implement all those methods
    List<EntryExitLog> findByMutantId(Long mutantId);
    List<EntryExitLog> findByEntryTimeAfter(LocalDateTime dateTime);
    List<EntryExitLog> findByExitTimeIsNull();
}
