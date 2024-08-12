package com.codecrafters.devs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recruitment_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mutant_id", nullable = false)
    private Mutant mutant;

    @Column(nullable = false)
    private boolean isEligibleForRecruitment;

    @Column(nullable = false)
    private int aliensDefeated;

    @Column(nullable = false)
    private int demonsDefeated;
}
