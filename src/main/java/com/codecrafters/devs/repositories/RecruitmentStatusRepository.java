package com.codecrafters.devs.repositories;

import com.codecrafters.devs.models.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentStatusRepository extends JpaRepository<RecruitmentStatus, Long> {
    List<RecruitmentStatus> findAllByIsEligibleForEspadaTrue();
}
