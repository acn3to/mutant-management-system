package com.codecrafters.devs.models;

import com.codecrafters.devs.enums.MutantRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "mutants")
@Table(name = "mutants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mutant implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String power;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MutantRole role = MutantRole.USER;

    @Column(nullable = false)
    private int enemiesDefeated = 0;

    @Column(nullable = false)
    private boolean isCurrentlyInSchool = true;

    @OneToMany(mappedBy = "mutant", cascade = CascadeType.ALL)
    private List<EntryExitLog> entryExitLogs;

    @OneToOne(mappedBy = "mutant", cascade = CascadeType.ALL)
    private RecruitmentStatus recruitmentStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == MutantRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
