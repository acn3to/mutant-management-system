package com.codecrafters.devs.repositories;

import com.codecrafters.devs.models.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long> {
    UserDetails findByUsername(String username);
    List<Mutant> findByIsCurrentlyInSchoolTrue();
    int countByIsCurrentlyInSchoolTrue();
}
