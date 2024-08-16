package com.codecrafters.devs.controllers;

import com.codecrafters.devs.dto.AuthenticationDTO;
import com.codecrafters.devs.dto.CreateMutantDTO;
import com.codecrafters.devs.dto.LoginResponseDTO;
import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.repositories.MutantRepository;
import com.codecrafters.devs.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MutantRepository mutantRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Mutant) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateMutantDTO data) {
        if (mutantRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Mutant newMutant = Mutant.builder()
                .name(data.name())
                .power(data.power())
                .age(data.age())
                .username(data.username())
                .password(encryptedPassword)
                .role(data.role())
                .build();

        mutantRepository.save(newMutant);

        return ResponseEntity.ok().build();
    }
}
