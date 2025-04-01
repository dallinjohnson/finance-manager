package com.dallinjohnson.financeManagerAPI.controller;

import com.dallinjohnson.financeManagerAPI.dto.AuthRequestDTO;
import com.dallinjohnson.financeManagerAPI.dto.AuthResponseDTO;
import com.dallinjohnson.financeManagerAPI.dto.RegisterDTO;
import com.dallinjohnson.financeManagerAPI.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
