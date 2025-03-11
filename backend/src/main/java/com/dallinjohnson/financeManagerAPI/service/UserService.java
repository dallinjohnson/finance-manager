package com.dallinjohnson.financeManagerAPI.service;

import com.dallinjohnson.financeManagerAPI.dto.LoginRequest;
import com.dallinjohnson.financeManagerAPI.dto.SignupRequest;
import com.dallinjohnson.financeManagerAPI.model.Role;
import com.dallinjohnson.financeManagerAPI.model.User;
import com.dallinjohnson.financeManagerAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Role defaultRole = Role.USER;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(SignupRequest signupRequest) {
        User user = new User();

        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.password());
        user.setPassword(encodedPassword);

        user.setEmail(signupRequest.email());
        user.setRole(defaultRole);

        userRepository.save(user);
    }

//    public String login(LoginRequest request) {
//        String username = request.username();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }
//    }
}
