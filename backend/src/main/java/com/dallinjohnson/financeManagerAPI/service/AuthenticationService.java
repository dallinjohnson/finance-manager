package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.AuthRequestDTO;
import com.dallinjohnson.financeManagerAPI.dto.AuthResponseDTO;
import com.dallinjohnson.financeManagerAPI.dto.RegisterDTO;
import com.dallinjohnson.financeManagerAPI.model.Role;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterDTO request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }
}
