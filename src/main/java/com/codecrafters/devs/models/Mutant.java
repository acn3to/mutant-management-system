package com.codecrafters.devs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "mutants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mutant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String power;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int enemiesDefeated;

    @Column(nullable = false)
    private boolean isCurrentlyInSchool;

    @OneToMany(mappedBy = "mutant", cascade = CascadeType.ALL)
    private List<EntryExitLog> entryExitLogs;

    @OneToOne(mappedBy = "mutant", cascade = CascadeType.ALL)
    private RecruitmentStatus recruitmentStatus;
}