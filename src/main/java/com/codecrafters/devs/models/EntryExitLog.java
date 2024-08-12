package com.codecrafters.devs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "entry_exit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryExitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mutant_id", nullable = false)
    private Mutant mutant;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;
}
